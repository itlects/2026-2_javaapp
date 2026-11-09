package edu.week15;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class TaskFlowTest {
    @Autowired MockMvc mvc;
    @Autowired JdbcTemplate jdbc;

    @BeforeEach
    void clear() { jdbc.update("DELETE FROM tasks"); }

    @Test
    void createUpdateSearchDelete() throws Exception {
        mvc.perform(get("/tasks/new")).andExpect(status().isOk())
                .andExpect(content().string(containsString("_csrf")));
        mvc.perform(post("/tasks").with(csrf()).param("title", "한글 과제")
                .param("status", "TODO")).andExpect(status().is3xxRedirection());
        long id = jdbc.queryForObject("SELECT id FROM tasks", Long.class);
        mvc.perform(get("/tasks/{id}/edit", id)).andExpect(status().isOk());
        mvc.perform(post("/tasks/{id}", id).with(csrf())
                .param("title", "검증 완료").param("status", "DONE"))
                .andExpect(redirectedUrl("/tasks"));
        mvc.perform(get("/tasks").param("q", "검증"))
                .andExpect(status().isOk()).andExpect(model().attribute("count", 1L))
                .andExpect(content().string(containsString("검증 완료")));
        mvc.perform(post("/tasks/{id}/delete", id).with(csrf()))
                .andExpect(redirectedUrl("/tasks"));
        assertThat(jdbc.queryForObject("SELECT COUNT(*) FROM tasks", Integer.class)).isZero();
    }

    @Test
    void rejectsInvalidInputAndMissingCsrf() throws Exception {
        mvc.perform(post("/tasks").param("title", "과제").param("status", "TODO"))
                .andExpect(status().isForbidden());
        mvc.perform(post("/tasks").with(csrf()).param("title", "   ")
                .param("status", "OTHER")).andExpect(view().name("form"))
                .andExpect(model().attributeHasFieldErrors("taskForm", "title", "status"));
        mvc.perform(post("/tasks").with(csrf()).param("title", "가".repeat(101))
                .param("status", "TODO"))
                .andExpect(model().attributeHasFieldErrors("taskForm", "title"));
        assertThat(jdbc.queryForObject("SELECT COUNT(*) FROM tasks", Integer.class)).isZero();
    }

    @Test
    void escapesOutputAndHandlesBoundaryRequests() throws Exception {
        jdbc.update("INSERT INTO tasks(title,status) VALUES (?,?)", "<script>alert(1)</script>", "TODO");
        mvc.perform(get("/tasks")).andExpect(content().string(containsString("&lt;script&gt;")))
                .andExpect(content().string(not(containsString("<script>alert(1)</script>"))));
        mvc.perform(get("/tasks").param("q", "' OR 1=1 --"))
                .andExpect(model().attribute("count", 0L));
        mvc.perform(get("/tasks").param("page", "-1")).andExpect(status().isBadRequest());
        mvc.perform(get("/tasks").param("page", "abc")).andExpect(status().isBadRequest());
        mvc.perform(get("/tasks/999999/edit")).andExpect(status().isNotFound());
        mvc.perform(get("/tasks/1/delete")).andExpect(status().isMethodNotAllowed());
    }

    @Test
    void paginationKeepsSearchAndReturnsFiveRows() throws Exception {
        for (int i = 1; i <= 7; i++) {
            jdbc.update("INSERT INTO tasks(title,status) VALUES (?,?)", "검색 과제 " + i, "TODO");
        }
        mvc.perform(get("/tasks").param("q", "검색").param("page", "0"))
                .andExpect(model().attribute("tasks", hasSize(5)))
                .andExpect(model().attribute("count", 7L));
        mvc.perform(get("/tasks").param("q", "검색").param("page", "1"))
                .andExpect(model().attribute("tasks", hasSize(2)));
    }
}

package edu.javaapp.member;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.containsString;

@SpringBootTest(properties = {
    "spring.datasource.url=jdbc:h2:mem:members;MODE=MySQL;DB_CLOSE_DELAY=-1",
    "spring.datasource.username=sa", "spring.datasource.password=",
    "spring.datasource.driver-class-name=org.h2.Driver",
    "spring.sql.init.mode=always"
})
@AutoConfigureMockMvc
@Transactional
class MemberFlowTest {
    @Autowired MockMvc mvc;
    @Autowired JdbcTemplate jdbc;

    @Test
    void crudAndRenderedTemplates() throws Exception {
        mvc.perform(get("/members/new")).andExpect(status().isOk())
                .andExpect(content().string(containsString("회원 입력")));
        mvc.perform(post("/members").param("name", "홍길동")
                .param("email", "hong@example.com").param("phone", "010-1111-2222"))
                .andExpect(redirectedUrl("/members"));
        Long id = jdbc.queryForObject("SELECT id FROM members WHERE email=?", Long.class, "hong@example.com");
        mvc.perform(get("/members")).andExpect(status().isOk())
                .andExpect(content().string(containsString("홍길동")));
        mvc.perform(get("/members/" + id + "/edit")).andExpect(status().isOk());
        mvc.perform(post("/members/" + id).param("name", "수정이름")
                .param("email", "hong@example.com").param("phone", "010-9999-8888"))
                .andExpect(redirectedUrl("/members"));
        assertEquals("수정이름", jdbc.queryForObject("SELECT name FROM members WHERE id=?", String.class, id));
        mvc.perform(post("/members/" + id + "/delete")).andExpect(redirectedUrl("/members"));
        assertEquals(0, jdbc.queryForObject("SELECT COUNT(*) FROM members", Integer.class));
    }

    @Test
    void invalidDuplicateMissingAndEscaping() throws Exception {
        mvc.perform(post("/members").param("name", " ").param("email", "bad").param("phone", "x"))
                .andExpect(model().attributeHasFieldErrors("memberForm", "name", "email", "phone"));
        jdbc.update("INSERT INTO members(name,email,phone) VALUES (?,?,?)", "<script>alert(1)</script>", "a@example.com", "010-1111-2222");
        mvc.perform(post("/members").param("name", "중복")
                .param("email", "a@example.com").param("phone", "010-1111-2222"))
                .andExpect(model().attributeHasFieldErrors("memberForm", "email"));
        mvc.perform(get("/members")).andExpect(content().string(containsString("&lt;script&gt;")));
        mvc.perform(get("/members/999999/edit")).andExpect(status().isNotFound());
        mvc.perform(get("/members/999999/delete")).andExpect(status().isMethodNotAllowed());
        assertEquals(1, jdbc.queryForObject("SELECT COUNT(*) FROM members", Integer.class));
    }
}

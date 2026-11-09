package com.example.members;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class MemberWebTest {
    @Autowired
    MockMvc mvc;

    @Test
    void listAndSearchRenderDatabaseRows() throws Exception {
        mvc.perform(get("/members"))
                .andExpect(status().isOk())
                .andExpect(view().name("members/list"))
                .andExpect(model().attribute("count", 5))
                .andExpect(content().string(containsString("minsu@example.com")));
        mvc.perform(get("/members").param("keyword", " 김 "))
                .andExpect(status().isOk())
                .andExpect(model().attribute("count", 2));
    }

    @Test
    void blankAndNoResultsAndInjectionAreHandled() throws Exception {
        mvc.perform(get("/members").param("keyword", "  "))
                .andExpect(model().attribute("count", 5));
        mvc.perform(get("/members").param("keyword", "없는회원"))
                .andExpect(model().attribute("count", 0))
                .andExpect(content().string(containsString("검색 결과가 없습니다.")));
        mvc.perform(get("/members").param("keyword", "' OR 1=1 --"))
                .andExpect(model().attribute("count", 0));
        mvc.perform(get("/members").param("keyword", "%"))
                .andExpect(model().attribute("count", 0));
    }

    @Test
    void detailAndInvalidIds() throws Exception {
        mvc.perform(get("/members/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("members/detail"))
                .andExpect(content().string(containsString("김민수")));
        mvc.perform(get("/members/999"))
                .andExpect(status().isNotFound());
        mvc.perform(get("/members/abc"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void helloEscapesInput() throws Exception {
        mvc.perform(get("/hello").param("name", "<script>alert(1)</script>"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("&lt;script&gt;")));
    }
}

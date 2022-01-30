package ru.job4j.controller;

import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.job4j.Job4jUrlShortcutApplication;
import ru.job4j.model.Site;
import ru.job4j.service.AppService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = Job4jUrlShortcutApplication.class)
@AutoConfigureMockMvc
public class SiteControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private Gson gson;

    @Autowired
    AppService service;

    @Test
    public void shouldReturnDefaultMessage() throws Exception {
        Site site = Site.of("URL");
        this.mockMvc.perform(post("/site/registration")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(gson.toJson(site)))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldReturnFalse() throws Exception {
        Site site = Site.of("URl");
        service.save(site);
        this.mockMvc.perform(post("/site/registration")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(gson.toJson(site)))
                .andExpect(content().string("registration : false. Site already registered"));
    }
}
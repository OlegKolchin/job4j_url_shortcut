package ru.job4j.controller;

import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import ru.job4j.Job4jUrlShortcutApplication;
import ru.job4j.model.Url;
import ru.job4j.service.AppService;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = Job4jUrlShortcutApplication.class)
@AutoConfigureMockMvc
public class UrlControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private Gson gson;

    @Autowired
    private AppService service;

    @Test
    @WithMockUser
    public void whenConvertReturnDefaultMessage() throws Exception {
        Map<String, String> request = new HashMap<>();
        request.put("url", "url");
        this.mockMvc.perform(post("/url/convert")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(gson.toJson(request)))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    public void whenConvertBadParamUnsuccessful() throws Exception {
        Map<String, String> request = new HashMap<>();
        request.put("urls", "url");
        this.mockMvc.perform(post("/url/convert")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(gson.toJson(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser
    public void whenUrlAlreadyExists() throws Exception {
        Map<String, String> request = new HashMap<>();
        Url url = Url.of("url2", null);
        service.save(url);
        request.put("url", "url2");
        this.mockMvc.perform(post("/url/convert")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(gson.toJson(request)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Invalid url value"));
    }

    @Test
    public void whenRedirectIsSuccessful() throws Exception {
        Url url = Url.of("https://job4j.ru/profile/exercise/106/task-view/532", "12345");
        service.save(url);
        this.mockMvc.perform(get("/url/redirect/12345"))
                .andExpect(status().isMovedPermanently());
    }

    @Test
    public void whenRedirectIsNotSuccessful() throws Exception {
        Url url = Url.of("https://job4j.ru/profile/exercise/106/task-view/533", "1234");
        service.save(url);
        this.mockMvc.perform(get("/url/redirect/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser
    public void whenShowStatistics() throws Exception {
        Url url = Url.of("test", "test");
        service.save(url);
        this.mockMvc.perform(get("/url/statistic"))
                .andExpect(status().isOk())
                .andExpect(content().string("[{\""
                        + "url\":\"https://job4j.ru/profile/exercise/106/task-view/532\""
                        + ",\"calls\":1}"
                        + ",{\"url\":\"test\",\"calls\":0}]"));
    }

}
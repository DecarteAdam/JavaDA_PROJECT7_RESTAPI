package com.nnk.springboot.controllers;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class HomeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser(authorities = "USER")
    public void home() throws Exception {
        this.mockMvc.perform(post("/")
                .with(csrf())
        ).andExpect(redirectedUrl("/bid/list"));
    }

    @Test
    public void getHome() throws Exception {
        this.mockMvc.perform(post("/")
                .with(csrf())
        ).andExpect(status().isOk());
    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    public void getAdminHome() throws Exception {
        this.mockMvc.perform(post("/admin/home")
                .with(csrf())
        ).andExpect(redirectedUrl("/bid/list"));
    }
}

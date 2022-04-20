package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    UserRepository userRepository;


    private User user;

    @Before
    public void setUp() {
        user = new User();
        user.setId(10);
        user.setFullname("John Doe");
        user.setUsername("john@test.com");
        user.setRole("USER");

    }


    @Test
    @WithMockUser(authorities = "USER")
    public void getUser() throws Exception {
        mockMvc.perform(get("/user/list")).andExpect(status().isOk());
    }

    @Test
    @WithMockUser(authorities = "USER")
    public void add() throws Exception {
        this.mockMvc.perform(get("/user/add")).andExpect(status().isOk());
    }

    @Test
    @WithMockUser(authorities = "USER")
    public void validate() throws Exception {
        this.mockMvc.perform(post("/user/validate")
                .param("id", "10")
                .param("fullname", "John Doe")
                .param("username", "john@test.com")
                .param("role", "USER")
                .with(csrf())
        ).andExpect(redirectedUrl("/user/list"));
    }


    @Test
    @WithMockUser
    public void updateForm() throws Exception {
        User user1 = userRepository.save(user);

        this.mockMvc.perform(get("/user/update/" + user1.getId()))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    public void updateNullId() {
        assertThatThrownBy(() ->
                mockMvc.perform(get("/user/update/" + 25))
        ).hasCause(new IllegalArgumentException("Invalid user Id:25"));
    }

    @Test
    @WithMockUser
    public void updateQuery() throws Exception {
        userRepository.save(user);

        this.mockMvc.perform(post("/user/update/" + user.getId())
                .param("id", "10")
                .param("fullname", "John Doe")
                .param("username", "john@test.com")
                .param("role", "USER")
                .with(csrf())
        ).andExpect(redirectedUrl("/user/list"));
    }


    @Test
    @WithMockUser
    public void updateWithError() throws Exception {
        userRepository.save(user);
        this.mockMvc.perform(post("/user/update/" + user.getId())
                .param("id", "id")
                .param("fullname", "John Doe")
                .param("username", "john@test.com")
                .param("role", "USER")
                .with(csrf())
        ).andExpect(model().hasErrors());
    }

    @Test
    @WithMockUser
    public void delete() throws Exception {
        User user1 = userRepository.save(user);

        this.mockMvc.perform(get("/user/delete/" + user1.getId()))
                .andExpect(status().isFound()).andReturn();
    }


    @Test
    @WithMockUser
    public void deleteNull() {
        assertThatThrownBy(() ->
                mockMvc.perform(get("/user/delete/" + 25))
        ).hasCause(new IllegalArgumentException("Invalid user Id:25"));
    }
}

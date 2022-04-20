package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.repositories.RatingRepository;
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
public class RatingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    RatingRepository ratingRepository;


    private Rating rating;

    @Before
    public void setUp() {
        rating = new Rating();
        rating.setMoodysRating("Mood");
        rating.setSandPRating("Sand");
        rating.setFitchRating("Fitch");
        rating.setOrderNumber(10);
    }


    @Test
    @WithMockUser(authorities = "USER")
    public void getRating() throws Exception {
        mockMvc.perform(get("/rating/list")).andExpect(status().isOk());
    }

    @Test
    @WithMockUser(authorities = "USER")
    public void add() throws Exception {
        this.mockMvc.perform(get("/rating/add")).andExpect(status().isOk());
    }

    @Test
    @WithMockUser(authorities = "USER")
    public void validate() throws Exception {
        this.mockMvc.perform(post("/rating/validate")
                .param("moodysRating", "Mood")
                .param("sandPRating", "Sand")
                .param("fitchRating", "Fitch")
                .param("orderNumber", "10")
                .with(csrf())
        ).andExpect(redirectedUrl("/rating/list"));
    }


    @Test
    @WithMockUser
    public void updateForm() throws Exception {
        Rating rating1 = ratingRepository.save(rating);

        this.mockMvc.perform(get("/rating/update/" + rating1.getId()))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    public void updateNullId() {
        assertThatThrownBy(() ->
                mockMvc.perform(get("/rating/update/" + 25))
        ).hasCause(new IllegalArgumentException("Invalid rating Id:25"));
    }

    @Test
    @WithMockUser
    public void updateQuery() throws Exception {
        ratingRepository.save(rating);

        this.mockMvc.perform(post("/rating/update/" + rating.getId())
                .param("moodysRating", "Mood")
                .param("sandPRating", "Sand")
                .param("fitchRating", "Fitch")
                .param("orderNumber", "10")
                .with(csrf())
        ).andExpect(redirectedUrl("/rating/list"));
    }


    @Test
    @WithMockUser
    public void updateWithError() throws Exception {
        ratingRepository.save(rating);
        this.mockMvc.perform(post("/rating/update/" + rating.getId())
                .param("moodysRating", "Mood")
                .param("sandPRating", "Sand")
                .param("fitchRating", "Fitch")
                .param("orderNumber", "Error*0")
                .with(csrf())
        ).andExpect(model().hasErrors());
    }

    @Test
    @WithMockUser
    public void delete() throws Exception {
        Rating rating1 = ratingRepository.save(rating);

        this.mockMvc.perform(get("/rating/delete/" + rating1.getId()))
                .andExpect(status().isFound()).andReturn();
    }


    @Test
    @WithMockUser
    public void deleteNull() {
        assertThatThrownBy(() ->
                mockMvc.perform(get("/rating/delete/" + 25))
        ).hasCause(new IllegalArgumentException("Invalid rating Id:25"));
    }
}

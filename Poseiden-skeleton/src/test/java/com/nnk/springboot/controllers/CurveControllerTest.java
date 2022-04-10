package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.repositories.CurvePointRepository;
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
public class CurveControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    CurvePointRepository curvePointRepository;


    private CurvePoint curve;

    @Before
    public void setUp() {
        curve = new CurvePoint();
        curve.setId(1);
        curve.setTerm(10d);
        curve.setValue(10d);
    }


    @Test
    @WithMockUser(authorities = "USER")
    public void getBidList() throws Exception {
        mockMvc.perform(get("/curvePoint/list")).andExpect(status().isOk());
    }

    @Test
    @WithMockUser(authorities = "USER")
    public void addCurve() throws Exception {
        this.mockMvc.perform(get("/curvePoint/add")).andExpect(status().isOk());
    }

    @Test
    @WithMockUser(authorities = "USER")
    public void validateCurve() throws Exception {
        this.mockMvc.perform(post("/curvePoint/validate")
                .param("id", "1")
                .param("term", "10d")
                .param("value", "10d")
                .with(csrf())
        ).andExpect(redirectedUrl("/curvePoint/list"));
    }


    @Test
    @WithMockUser
    public void updateForm() throws Exception {
        CurvePoint curvePoint = curvePointRepository.save(curve);

        this.mockMvc.perform(get("/curvePoint/update/" + curvePoint.getId()))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    public void updateCurveNullId() {
        assertThatThrownBy(() ->
                mockMvc.perform(get("/curvePoint/update/" + 25))
        ).hasCause(new IllegalArgumentException("Invalid curvePoint Id:25"));
    }

    @Test
    @WithMockUser
    public void updateQuery() throws Exception {
        curvePointRepository.save(curve);

        this.mockMvc.perform(post("/curvePoint/update/" + curve.getId())
                .param("id", "1")
                .param("term", "10d")
                .param("value", "10d")
                .with(csrf())
        ).andExpect(redirectedUrl("/curvePoint/list"));
    }

    /*@Test
    @WithMockUser
    public void updateQueryErrors() throws Exception {


        this.mockMvc.perform(post("/curvePoint/validate/")
                .param("account", "Account")
                .param("type", "Test type")
                .param("cyrveQuantity", "Error*0")
                .with(csrf())
        ).andExpect(redirectedUrl("/curvePoint/add"));
    }*/

    @Test
    @WithMockUser
    public void updateCurveWithError() throws Exception {
        curvePointRepository.save(curve);
        this.mockMvc.perform(post("/curvePoint/update/" + curve.getId())
                .param("id", "1")
                .param("term", "10d")
                .param("value", "Error*0")
                .with(csrf())
        ).andExpect(model().hasErrors());
    }

    @Test
    @WithMockUser
    public void deleteCurve() throws Exception {
        CurvePoint curvePoint = curvePointRepository.save(curve);

        this.mockMvc.perform(get("/curvePoint/delete/" + curvePoint.getId()))
                .andExpect(status().isFound()).andReturn();
    }


    @Test
    @WithMockUser
    public void deleteCurveNull() {
        assertThatThrownBy(() ->
                mockMvc.perform(get("/curvePoint/delete/" + 25))
        ).hasCause(new IllegalArgumentException("Invalid curvePoint Id:25"));
    }
}

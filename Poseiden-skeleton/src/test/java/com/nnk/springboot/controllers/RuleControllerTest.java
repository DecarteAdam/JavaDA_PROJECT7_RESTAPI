package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.Rule;
import com.nnk.springboot.repositories.RuleRepository;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class RuleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private RuleRepository ruleRepository;


    private Rule rule;

    @Before
    public void setUp() {
        rule = new Rule();
        rule.setName("Name");
        rule.setDescription("Description");
        rule.setJson("JSON");
        rule.setTemplate("Template");
        rule.setSqlPart("SQLPart");
        rule.setSqlStr("SQLStart");

    }


    @Test
    @WithMockUser(authorities = "USER")
    public void getRule() throws Exception {
        mockMvc.perform(get("/rule/list")).andExpect(status().isOk());
    }

    @Test
    @WithMockUser(authorities = "USER")
    public void add() throws Exception {
        this.mockMvc.perform(get("/rule/add")).andExpect(status().isOk());
    }

    @Test
    @WithMockUser(authorities = "USER")
    public void validate() throws Exception {
        this.mockMvc.perform(post("/rule/validate")
                .param("name", "Name")
                .param("description", "Description")
                .param("json", "JSON")
                .param("template", "Template")
                .param("sqlPart", "SQLPart")
                .param("sqlStart", "SQLStart")
                .with(csrf())
        ).andExpect(redirectedUrl("/rule/list"));
    }


    @Test
    @WithMockUser
    public void updateForm() throws Exception {
        Rule rule1 = ruleRepository.save(rule);

        this.mockMvc.perform(get("/rule/update/" + rule1.getId()))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    public void updateNullId() {
        assertThatThrownBy(() ->
                mockMvc.perform(get("/rule/update/" + 25))
        ).hasCause(new IllegalArgumentException("Invalid rule Id:25"));
    }

    @Test
    @WithMockUser
    public void updateQuery() throws Exception {
        ruleRepository.save(rule);

        this.mockMvc.perform(post("/rule/update/" + rule.getId())
                .param("name", "Name")
                .param("description", "Description")
                .param("json", "JSON")
                .param("template", "Template")
                .param("sqlPart", "SQLPart")
                .param("sqlStart", "SQLStart")
                .with(csrf())
        ).andExpect(redirectedUrl("/rule/list"));
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

    /*@Test
    @WithMockUser
    public void updateWithError() throws Exception {
        ruleRepository.save(rule);
        this.mockMvc.perform(post("/rule/update/" + rule.getId())
                .param("name", "Name")
                .param("description", "Description")
                .param("json", "JSON")
                .param("template", "Template")
                .param("sqlPart", "SQLPart")
                .param("sqlStart", "SQLStart")
                .with(csrf())
        ).andExpect(model().hasErrors());
    }*/

    @Test
    @WithMockUser
    public void delete() throws Exception {
        Rule rule1 = ruleRepository.save(rule);

        this.mockMvc.perform(get("/rule/delete/" + rule1.getId()))
                .andExpect(status().isFound()).andReturn();
    }


    @Test
    @WithMockUser
    public void deleteNull() {
        assertThatThrownBy(() ->
                mockMvc.perform(get("/rule/delete/" + 25))
        ).hasCause(new IllegalArgumentException("Invalid rule Id:25"));
    }
}

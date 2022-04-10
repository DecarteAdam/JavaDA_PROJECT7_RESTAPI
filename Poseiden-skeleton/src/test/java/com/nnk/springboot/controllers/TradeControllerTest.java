package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.repositories.TradeRepository;
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
public class TradeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TradeRepository tradeRepository;


    private Trade trade;

    @Before
    public void setUp() {
        trade = new Trade();
        trade.setTradeId(10);
        trade.setAccount("Account");
        trade.setType("Type");
        trade.setBuyQuantity(10d);
    }


    @Test
    @WithMockUser(authorities = "USER")
    public void getTrade() throws Exception {
        mockMvc.perform(get("/trade/list")).andExpect(status().isOk());
    }

    @Test
    @WithMockUser(authorities = "USER")
    public void add() throws Exception {
        this.mockMvc.perform(get("/trade/add")).andExpect(status().isOk());
    }

    /*@Test
    @WithMockUser(authorities = "USER")
    public void validate() throws Exception {
        this.mockMvc.perform(post("/trade/validate")
                .param("tradeId", "Mood")
                .param("account", "Sand")
                .param("type", "Fitch")
                .param("buyQuantity", "10d")
                .with(csrf())
        ).andExpect(redirectedUrl("/trade/list"));
    }*/


    @Test
    @WithMockUser
    public void updateForm() throws Exception {
        Trade trade1 = tradeRepository.save(trade);

        this.mockMvc.perform(get("/trade/update/" + trade1.getTradeId()))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    public void updateNullId() {
        assertThatThrownBy(() ->
                mockMvc.perform(get("/trade/update/" + 25))
        ).hasCause(new IllegalArgumentException("Invalid trade Id:25"));
    }

    @Test
    @WithMockUser
    public void updateQuery() throws Exception {
        tradeRepository.save(trade);

        this.mockMvc.perform(post("/trade/update/" + trade.getTradeId())
                .param("moodysRating", "Mood")
                .param("sandPRating", "Sand")
                .param("fitchRating", "Fitch")
                .param("orderNumber", "10")
                .with(csrf())
        ).andExpect(redirectedUrl("/trade/list"));
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
    public void updateWithError() throws Exception {
        tradeRepository.save(trade);
        this.mockMvc.perform(post("/trade/update/" + trade.getTradeId())
                .param("tradeId", "Mood")
                .param("account", "Sand")
                .param("type", "Fitch")
                .param("buyQuantity", "Error*0")
                .with(csrf())
        ).andExpect(model().hasErrors());
    }

    @Test
    @WithMockUser
    public void delete() throws Exception {
        Trade trade1 = tradeRepository.save(trade);

        this.mockMvc.perform(get("/trade/delete/" + trade1.getTradeId()))
                .andExpect(status().isFound()).andReturn();
    }


    @Test
    @WithMockUser
    public void deleteNull() {
        assertThatThrownBy(() ->
                mockMvc.perform(get("/trade/delete/" + 25))
        ).hasCause(new IllegalArgumentException("Invalid trade Id:25"));
    }
}

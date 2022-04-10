package com.nnk.springboot.controllers;


import com.nnk.springboot.domain.Bid;
import com.nnk.springboot.repositories.BidRepository;
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
public class BidControllerTest {

    @Autowired
   private MockMvc mockMvc;

    @Autowired
    BidRepository bidRepository;


    private Bid bid;

    @Before
    public void setUp(){
        bid = new Bid();
        bid.setAccount("Account");
        bid.setType("Test type");
        bid.setBidQuantity(10d);
    }


    @Test
    @WithMockUser(authorities = "USER")
    public void getBidList() throws Exception {
        mockMvc.perform(get("/bid/list")).andExpect(status().isOk());
    }

    @Test
    @WithMockUser(authorities = "USER")
    public void addBid() throws Exception {
        this.mockMvc.perform(get("/bid/add")).andExpect(status().isOk());
    }

    @Test
    @WithMockUser(authorities = "USER")
    public void validateBid() throws Exception {
        this.mockMvc.perform(post("/bid/validate")
                .param("Account", "Test Account")
                .param("type", "Test type")
                .param("bidQuantity", "10")
                .with(csrf())
        ).andExpect(redirectedUrl("/bid/list"));
    }



    @Test
    @WithMockUser
    public void updateForm() throws Exception {
        bidRepository.save(bid);

        this.mockMvc.perform(get("/bid/update/" + bid.getId())).andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    public void updateBidNullId() {
        assertThatThrownBy(() ->
                mockMvc.perform(get("/bid/update/" + 25))
        ).hasCause(new IllegalArgumentException("Invalid bid Id:25"));
    }

    @Test
    @WithMockUser
    public void updateQuery() throws Exception {
        bidRepository.save(bid);

        this.mockMvc.perform(post("/bid/update/" + bid.getId())
                .param("account", "Account")
                .param("type", "Test type")
                .param("bidQuantity", "20")
                .with(csrf())
        ).andExpect(redirectedUrl("/bid/list"));
    }

    /*@Test
    @WithMockUser
    public void updateQueryErrors() throws Exception {


        this.mockMvc.perform(post("/bid/validate/")
                .param("account", "Account")
                .param("type", "Test type")
                .param("bidQuantity", "Error*0")
                .with(csrf())
        ).andExpect(redirectedUrl("/bid/add"));
    }*/

    @Test
    @WithMockUser
    public void updateBidWithError() throws Exception {
        bidRepository.save(bid);
        this.mockMvc.perform(post("/bid/update/" + bid.getId())
                .param("account", "Account")
                .param("type", "Test type")
                .param("bidQuantity", "Error*0")
                .with(csrf())
        ).andExpect(model().hasErrors());
    }

    @Test
    @WithMockUser
    public void deleteBid() throws Exception {
        Bid bid1 = bidRepository.save(bid);

        this.mockMvc.perform(get("/bid/delete/" + bid1.getId()))
                .andExpect(status().isFound()).andReturn();
    }


    @Test
    @WithMockUser
    public void deleteBidNull() {
        assertThatThrownBy(() ->
                mockMvc.perform(get("/bid/delete/" + 25))
        ).hasCause(new IllegalArgumentException("Invalid bid Id:25"));
    }
}

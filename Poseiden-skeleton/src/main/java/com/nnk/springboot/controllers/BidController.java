package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.Bid;
import com.nnk.springboot.services.BidService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@AllArgsConstructor
public class BidController {

    BidService bidService;

    /**
     * Display BidList list
     * @param model bidLists do display
     * @return list page
     */
    @RequestMapping("/bid/list")
    public String home(Model model) {
        // TODO: call service find all bids to show to the view
        model.addAttribute("bidList", bidService.findAll());
        return "bid/list";
    }

    /**
     * Dsiplay add bidList form
     * @param bid
     * @return add form page
     */
    @GetMapping("/bid/add")
    public String addBidForm(Bid bid) {
        return "bid/add";
    }

    /**
     *
     * @param bid bidList to validate & save
     * @param result errors to check
     * @param model bidLists to display
     * @return list if everything is ok else return to add form page
     */
    @PostMapping("/bid/validate")
    public String validate(@Valid Bid bid, BindingResult result, Model model) {
        // TODO: check data valid and save to db, after saving return bid list
        if (!result.hasErrors()) {
            bidService.save(bid);
            model.addAttribute("bidList", this.bidService.findAll());
            return "redirect:/bid/list";
        }
        return "bid/add";
    }

    /**
     * Display edit form page
     * @param id of bidList to edit
     * @param model bidLists to display
     * @return bidList update page
     */
    @GetMapping("/bid/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        // TODO: get Bid by Id and to model then show to the form
        Bid bid = bidService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid bid Id:" + id));
        model.addAttribute("bid", bid);
        return "bid/update";
    }

    /***
     * Update bidList by its id
     * @param id of bidList to update
     * @param bid bidList to update
     * @param result errors to check
     * @param model bidLists to display
     * @return bidLists page if everything is ok else return to update page
     */
    @PostMapping("/bid/update/{id}")
    public String updateBid(@PathVariable("id") Integer id, @Valid Bid bid,
                             BindingResult result, Model model) {
        // TODO: check required fields, if valid call service to update Bid and return list Bid
        if (result.hasErrors()) {
            return "bid/update";
        }

        bid.setId(id);
        bidService.save(bid);
        model.addAttribute("bidList", bidService.findAll());
        return "redirect:/bid/list";
    }

    /**
     * Delete bidList by its id
     * @param id of bidList to delete
     * @param model of bidLists to display
     * @return list page
     */
    @GetMapping("/bid/delete/{id}")
    public String deleteBid(@PathVariable("id") Integer id, Model model) {
        // TODO: Find Bid by Id and delete the bid, return to Bid list
        Bid bid = bidService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid bid Id:" + id));
        bidService.deleteById(bid.getId());
        model.addAttribute("bidList", bidService.findAll());
        return "redirect:/bid/list";
    }
}

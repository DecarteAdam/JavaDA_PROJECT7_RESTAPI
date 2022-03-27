package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.services.BidListService;
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
public class BidListController {

    BidListService bidListService;

    /**
     * Display BidList list
     * @param model bidLists do display
     * @return list page
     */
    @RequestMapping("/bidList/list")
    public String home(Model model) {
        // TODO: call service find all bids to show to the view
        model.addAttribute("bidList", bidListService.getBidLists());
        return "bidList/list";
    }

    /**
     * Dsiplay add bidList form
     * @param bid
     * @return add form page
     */
    @GetMapping("/bidList/add")
    public String addBidForm(BidList bid) {
        return "bidList/add";
    }

    /**
     *
     * @param bid bidList to validate & save
     * @param result errors to check
     * @param model bidLists to display
     * @return list if everything is ok else return to add form page
     */
    @PostMapping("/bidList/validate")
    public String validate(@Valid BidList bid, BindingResult result, Model model) {
        // TODO: check data valid and save to db, after saving return bid list
        if (!result.hasErrors()) {
            bidListService.add(bid);
            model.addAttribute("bidList", this.bidListService.getBidLists());
            return "redirect:/bidList/list";
        }
        return "bidList/add";
    }

    /**
     * Display edit form page
     * @param id of bidList to edit
     * @param model bidLists to display
     * @return bidList update page
     */
    @GetMapping("/bidList/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        // TODO: get Bid by Id and to model then show to the form
        BidList bidList = bidListService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid bidList Id:" + id));
        model.addAttribute("bidList", bidList);
        return "bidList/update";
    }

    /***
     *
     * @param id of bidList to update
     * @param bidList bidList to update
     * @param result errors to check
     * @param model bidLists to display
     * @return bidLists page if everything is ok else return to update page
     */
    @PostMapping("/bidList/update/{id}")
    public String updateBid(@PathVariable("id") Integer id, @Valid BidList bidList,
                             BindingResult result, Model model) {
        // TODO: check required fields, if valid call service to update Bid and return list Bid
        if (result.hasErrors()) {
            return "bidList/update";
        }

        bidList.setBidListId(id);
        bidListService.add(bidList);
        model.addAttribute("bidList", bidListService.getBidLists());
        return "redirect:/bidList/list";
    }

    /**
     * Delete bidList by its id
     * @param id of bidList to delete
     * @param model of bidLists to display
     * @return list page
     */
    @GetMapping("/bidList/delete/{id}")
    public String deleteBid(@PathVariable("id") Integer id, Model model) {
        // TODO: Find Bid by Id and delete the bid, return to Bid list
        BidList bidList = bidListService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid bidList Id:" + id));
        bidListService.deleteBidList(bidList.getBidListId());
        model.addAttribute("bidList", bidListService.getBidLists());
        return "redirect:/bidList/list";
    }
}

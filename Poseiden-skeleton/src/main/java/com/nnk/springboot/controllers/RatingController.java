package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.services.RatingService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@AllArgsConstructor
public class RatingController {
    private static final Logger logger = LoggerFactory.getLogger(RatingController.class);

    private RatingService ratingService;

    @RequestMapping("/rating/list")
    public String home(Model model) {
        logger.debug("GET: /rating/list");
        model.addAttribute("ratings", ratingService.findAll());
        return "rating/list";
    }

    @GetMapping("/rating/add")
    public String addRatingForm(Rating rating) {
        return "rating/add";
    }

    @PostMapping("/rating/validate")
    public String validate(@Valid Rating rating, BindingResult result, Model model) {
        logger.debug("POST: /rating/add");
        if (!result.hasErrors()) {
            ratingService.save(rating);
            model.addAttribute("rating", this.ratingService.findAll());
            return "redirect:/rating/list";
        }
        return "rating/add";
    }

    @GetMapping("/rating/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        logger.debug("GET: /rating/update/{}", id);
        Rating rating = ratingService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid rating Id:" + id));
        model.addAttribute("rating", rating);
        return "rating/update";
    }

    @PostMapping("/rating/update/{id}")
    public String updateRating(@PathVariable("id") Integer id, @Valid Rating rating,
                               BindingResult result, Model model) {
        logger.debug("GET: /rating/update/{}", id);
        if (result.hasErrors()) {
            return "rating/update";
        }

        rating.setId(id);
        ratingService.save(rating);
        model.addAttribute("rating", ratingService.findAll());
        return "redirect:/rating/list";
    }

    @GetMapping("/rating/delete/{id}")
    public String deleteRating(@PathVariable("id") Integer id, Model model) {
        logger.debug("GET: /rating/delete/{}", id);
        Rating rating = ratingService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid rating Id:" + id));
        ratingService.deleteById(rating.getId());
        model.addAttribute("rating", ratingService.findAll());
        return "redirect:/rating/list";
    }
}

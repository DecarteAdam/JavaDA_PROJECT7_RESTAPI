package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.services.TradeService;
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
public class TradeController {
    private static final Logger logger = LoggerFactory.getLogger(TradeController.class);
    private TradeService tradeService;

    @RequestMapping("/trade/list")
    public String home(Model model) {
        logger.debug("GET: /trade/list");
        model.addAttribute("trades", tradeService.findAll());
        return "trade/list";
    }

    @GetMapping("/trade/add")
    public String addTrade(Trade bid) {
        return "trade/add";
    }

    @PostMapping("/trade/validate")
    public String validate(@Valid Trade trade, BindingResult result, Model model) {
        logger.debug("GET: /trade/validate");
        if (!result.hasErrors()) {
            tradeService.save(trade);
            model.addAttribute("trades", this.tradeService.findAll());
            return "redirect:/trade/list";
        }
        return "trade/add";
    }

    @GetMapping("/trade/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        logger.debug("GET: /trade/update/{}", id);
        Trade trade = tradeService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid trade Id:" + id));
        model.addAttribute("trade", trade);
        return "trade/update";
    }

    @PostMapping("/trade/update/{id}")
    public String updateTrade(@PathVariable("id") Integer id, @Valid Trade trade,
                              BindingResult result, Model model) {
        logger.debug("GET: /trade/update/{}", id);
        if (result.hasErrors()) {
            return "trade/update";
        }

        trade.setTradeId(id);
        tradeService.save(trade);
        model.addAttribute("trades", tradeService.findAll());
        return "redirect:/trade/list";
    }

    @GetMapping("/trade/delete/{id}")
    public String deleteTrade(@PathVariable("id") Integer id, Model model) {
        logger.debug("GET: /trade/delete/{}", id);
        Trade trade = tradeService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid trade Id:" + id));
        tradeService.deleteById(trade.getTradeId());
        model.addAttribute("trades", tradeService.findAll());
        return "redirect:/trade/list";
    }
}

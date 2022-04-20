package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.Rule;
import com.nnk.springboot.services.RuleService;
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
public class RuleController {
    private static final Logger logger = LoggerFactory.getLogger(RuleController.class);
    private RuleService ruleService;

    @RequestMapping("/rule/list")
    public String home(Model model) {
        logger.debug("GET: /rule/list");
        model.addAttribute("ruleNames", ruleService.findAll());
        return "rule/list";
    }

    @GetMapping("/rule/add")
    public String addRuleForm(Rule bid) {
        logger.debug("GET: rule/add/");
        return "rule/add";
    }

    @PostMapping("/rule/validate")
    public String validate(@Valid Rule rule, BindingResult result, Model model) {
        logger.debug("GET: /rule/validate");
        if (!result.hasErrors()) {
            ruleService.save(rule);
            model.addAttribute("ruleNames", this.ruleService.findAll());
            return "redirect:/rule/list";
        }
        return "rule/add";
    }

    @GetMapping("/rule/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        logger.debug("GET: /rule/update/{}", id);
        Rule rule = ruleService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid rule Id:" + id));
        model.addAttribute("rule", rule);
        return "rule/update";
    }

    @PostMapping("/rule/update/{id}")
    public String updateRule(@PathVariable("id") Integer id, @Valid Rule rule,
                             BindingResult result, Model model) {
        logger.debug("GET: /rule/update/{}", id);
        if (result.hasErrors()) {
            return "rule/update";
        }

        rule.setId(id);
        ruleService.save(rule);
        model.addAttribute("ruleNames", ruleService.findAll());
        return "redirect:/rule/list";
    }

    @GetMapping("/rule/delete/{id}")
    public String deleteRule(@PathVariable("id") Integer id, Model model) {
        logger.debug("GET: /rule/delete/{}", id);
        Rule rule = ruleService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid rule Id:" + id));
        ruleService.deleteById(rule.getId());
        model.addAttribute("ruleNames", ruleService.findAll());
        return "redirect:/rule/list";
    }
}

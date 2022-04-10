package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.Rule;
import com.nnk.springboot.services.RuleService;
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
public class RuleController {
    // TODO: Inject RuleName service

    RuleService ruleService;

    @RequestMapping("/rule/list")
    public String home(Model model)
    {
        // TODO: find all RuleName, add to model
        model.addAttribute("ruleNames", ruleService.findAll());
        return "rule/list";
    }

    @GetMapping("/rule/add")
    public String addRuleForm(Rule bid) {
        return "rule/add";
    }

    @PostMapping("/rule/validate")
    public String validate(@Valid Rule rule, BindingResult result, Model model) {
        // TODO: check data valid and save to db, after saving return RuleName list
        if (!result.hasErrors()) {
            ruleService.save(rule);
            model.addAttribute("ruleNames", this.ruleService.findAll());
            return "redirect:/rule/list";
        }
        return "rule/add";
    }

    @GetMapping("/rule/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        // TODO: get RuleName by Id and to model then show to the form
        Rule rule = ruleService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid rule Id:" + id));
        model.addAttribute("rule", rule);
        return "rule/update";
    }

    @PostMapping("/rule/update/{id}")
    public String updateRule(@PathVariable("id") Integer id, @Valid Rule rule,
                             BindingResult result, Model model) {
        // TODO: check required fields, if valid call service to update RuleName and return RuleName list
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
        // TODO: Find RuleName by Id and delete the RuleName, return to Rule list
        Rule rule = ruleService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid rule Id:" + id));
        ruleService.deleteById(rule.getId());
        model.addAttribute("ruleNames", ruleService.findAll());
        return "redirect:/rule/list";
    }
}

package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.services.CurveService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@AllArgsConstructor
@Controller
public class CurveController {
    // TODO: Inject Curve Point service

    CurveService curveService;

    /**
     * Display curvePoint list
     * @param model curvePoint do display
     * @return list page
     */
    @RequestMapping("/curvePoint/list")
    public String home(Model model)
    {
        // TODO: find all Curve Point, add to model
        model.addAttribute("curvePoint", curveService.findAll());
        return "curvePoint/list";
    }

    /**
     * Dsiplay add curvePoint form
     * @param bid
     * @return add form page
     */
    @GetMapping("/curvePoint/add")
    public String addCurvePointForm(CurvePoint bid) {
        return "curvePoint/add";
    }

    /**
     *
     * @param curvePoint curvePoint to validate & save
     * @param result errors to check
     * @param model curvePoint to display
     * @return list if everything is ok else return to add form page
     */
    @PostMapping("/curvePoint/validate")
    public String validate(@Valid CurvePoint curvePoint, BindingResult result, Model model) {
        // TODO: check data valid and save to db, after saving return Curve list
        if (!result.hasErrors()) {
            curveService.save(curvePoint);
            model.addAttribute("curvePoint", this.curveService.findAll());
            return "redirect:/curvePoint/list";
        }
        return "curvePoint/add";
    }

    /**
     * Display edit form page
     * @param id of curvePoint to edit
     * @param model curvePoints to display
     * @return curvePoints update page
     */
    @GetMapping("/curvePoint/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        // TODO: get CurvePoint by Id and to model then show to the form
        CurvePoint curvePoint = curveService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid curvePoint Id:" + id));
        model.addAttribute("curvePoint", curvePoint);
        return "curvePoint/update";
    }

    /***
     * Update curvePoint by its id
     * @param id of curvePoint to update
     * @param curvePoint curvePoint to update
     * @param result errors to check
     * @param model curvePoints to display
     * @return curvePoints page if everything is ok else return to update page
     */
    @PostMapping("/curvePoint/update/{id}")
    public String updateCurvePoint(@PathVariable("id") Integer id, @Valid CurvePoint curvePoint,
                             BindingResult result, Model model) {
        // TODO: check required fields, if valid call service to update Curve and return Curve list
        if (result.hasErrors()) {
            return "curvePoint/update";
        }

        curvePoint.setCurveId(id);
        curveService.save(curvePoint);
        model.addAttribute("curvePoint", curveService.findAll());
        return "redirect:/curvePoint/list";
    }

    /**
     * Delete curvePoint by its id
     * @param id of curvePoint to delete
     * @param model of curvePoints to display
     * @return list page
     */
    @GetMapping("/curvePoint/delete/{id}")
    public String deleteCurvePoint(@PathVariable("id") Integer id, Model model) {
        // TODO: Find Curve by Id and delete the Curve, return to Curve list
        CurvePoint curvePoint = curveService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid curvePoint Id:" + id));
        curveService.deleteById(curvePoint.getId());
        model.addAttribute("curvePoint", curveService.findAll());
        return "redirect:/curvePoint/list";
    }
}

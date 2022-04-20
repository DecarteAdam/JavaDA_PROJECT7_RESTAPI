package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.services.CurveService;
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

@AllArgsConstructor
@Controller
public class CurveController {
    private static final Logger logger = LoggerFactory.getLogger(CurvePoint.class);

    private CurveService curveService;

    /**
     * Display curvePoint list
     * @param model curvePoint do display
     * @return list page
     */
    @RequestMapping("/curvePoint/list")
    public String home(Model model) {
        logger.debug("GET: /curvePoint/list");
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
        logger.debug("GET: /curvePoint/add");
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
        logger.debug("POST: /curvePoint/validate");
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
        logger.debug("GET: /curvePoint/update/{}", id);
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
        logger.debug("POST: /curvePoint/update/{}", id);
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
        logger.debug("GET: /curvePoint/delete{}", id);
        CurvePoint curvePoint = curveService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid curvePoint Id:" + id));
        curveService.deleteById(curvePoint.getId());
        model.addAttribute("curvePoint", curveService.findAll());
        return "redirect:/curvePoint/list";
    }
}

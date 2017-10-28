package com.huotu.loanmarket.web.controller.backgroud;

import com.huotu.loanmarket.service.entity.LoanCategory;
import com.huotu.loanmarket.service.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author allan
 * @date 26/10/2017
 */
@Controller
@RequestMapping("/backend/project")
public class ProjectController {
    @Autowired
    private CategoryService categoryService;

    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public String edit(
            @RequestParam(required = false, defaultValue = "0") int projectId,
            Model model
    ) {
        List<LoanCategory> categories = categoryService.getAll();
        model.addAttribute("categories", categories);
        return "edit_project";
    }
}

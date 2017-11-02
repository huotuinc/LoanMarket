package com.huotu.loanmarket.web.controller.loanweb;

import com.huotu.loanmarket.service.entity.LoanProject;
import com.huotu.loanmarket.service.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 *
 * @author hxh
 * @date 2017-10-30
 */
@Controller
@RequestMapping("/forend")
public class WebIndexController {
    @Autowired
    private ProjectService projectService;
    @RequestMapping("/index")
    public String index(Model model){
        List<LoanProject> hotProject = projectService.getHotProject();
        List<LoanProject> newProject = projectService.getNewProject();
        model.addAttribute("hotProject",hotProject);
        model.addAttribute("newProject",newProject);
        return "forend/home";
    }

}


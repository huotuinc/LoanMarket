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
 * @date 2017-10-31
 */
@Controller
@RequestMapping("/forend/project")
public class WebProjectController {
    @Autowired
    private ProjectService projectService;
    @RequestMapping("/getDetail")
    public String getProjectDetail(int projectId, Model model){
        LoanProject loanProject = projectService.findOne(projectId);
        String[] deadline = loanProject.getDeadline().split(",");
        model.addAttribute("minDeadline",deadline[0]);
        model.addAttribute("maxDeadline",deadline[deadline.length-1]);
        model.addAttribute("loanProject",loanProject);
        return "forend/loanDetail";
    }
    @RequestMapping("/loanProcess")
    public String loanProcess(Model model){
        List<LoanProject> hotProject = projectService.getHotProject();
        model.addAttribute("hotProject",hotProject);
        return "forend/loanProgress";
    }
}

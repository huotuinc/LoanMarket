package com.huotu.loanmarket.webapi.controller.project;

import com.huotu.loanmarket.service.entity.project.Project;
import com.huotu.loanmarket.service.service.project.ProjectService;
import com.huotu.loanmarket.service.service.upload.StaticResourceService;
import com.huotu.loanmarket.webapi.controller.sesame.SesameController;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import java.net.URISyntaxException;
import java.util.List;

@RequestMapping("/api/projectView")
@Controller
public class ProjectViewController {
    private static final Log log = LogFactory.getLog(ProjectController.class);
    @Autowired
    private StaticResourceService staticResourceService;
    @Autowired
    private ProjectService projectService;

    @RequestMapping("/loanProcess")
    public String loanProcess(Model model) {
        List<Project> hotProject = projectService.getHotProject();
        hotProject.forEach(p -> {
            if (!StringUtils.isEmpty(p.getLogo())) {
                try {
                    p.setLogo(staticResourceService.getResource(p.getLogo()).toString());
                } catch (URISyntaxException e) {
                    log.error("获取图片异常：" + e);
                }
            }
        });
        model.addAttribute("hotProject", hotProject);
        return "project/loanProgress";
    }
}

package com.huotu.loanmarket.web.controller.loanweb;

import com.huotu.loanmarket.common.SysConstant;
import com.huotu.loanmarket.common.utils.CookieHelper;
import com.huotu.loanmarket.service.entity.LoanProject;
import com.huotu.loanmarket.service.service.ProjectService;
import com.huotu.loanmarket.service.service.UserService;
import com.huotu.loanmarket.web.service.StaticResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author hxh
 * @date 2017-10-30
 */
@Controller
@RequestMapping("/forend")
public class WebIndexController {
    @Autowired
    private ProjectService projectService;
    @Autowired
    private StaticResourceService staticResourceService;

    @RequestMapping("/index")
    public String index(Model model) {
        List<LoanProject> hotProject = projectService.getHotProject();
        List<LoanProject> newProject = projectService.getNewProject();
        List<LoanProject> hotModel = new ArrayList<>();
        hotProject.forEach(p -> {
            if (!StringUtils.isEmpty(p.getLogo())) {
                try {
                    p.setLogo(staticResourceService.get(p.getLogo()).toString());
                } catch (URISyntaxException e) {
                }
            }
            if (!StringUtils.isEmpty(p.getTag()) && p.getTag().split(",").length > 3) {
                String[] tags = p.getTag().split(",");
                String tag = tags[0] + "," + tags[1] + "," + tags[2];
                p.setTag(tag);
            }
        });
        if (hotProject.size() > SysConstant.HOT_LIST_SIZE) {
            for (int i = 0; i < SysConstant.HOT_LIST_SIZE; i++) {
                hotModel.add(hotProject.get(i));
            }
        } else {
            hotModel = hotProject;
        }
        newProject.forEach(p -> {
            if (!StringUtils.isEmpty(p.getLogo())) {
                try {
                    p.setLogo(staticResourceService.get(p.getLogo()).toString());
                } catch (URISyntaxException e) {
                }
            }
            if (!StringUtils.isEmpty(p.getTag()) && p.getTag().split(",").length > 3) {
                String[] tags = p.getTag().split(",");
                String tag = tags[0] + "," + tags[1] + "," + tags[2];
                p.setTag(tag);
            }
        });
        model.addAttribute("hotProject", hotModel);
        model.addAttribute("newProject", newProject);
        return "forend/home";
    }
}


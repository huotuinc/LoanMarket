package com.huotu.loanmarket.web.controller.loanweb;

import com.huotu.loanmarket.service.entity.LoanCategory;
import com.huotu.loanmarket.service.entity.LoanProject;
import com.huotu.loanmarket.service.service.CategoryService;
import com.huotu.loanmarket.service.service.ProjectService;
import com.huotu.loanmarket.web.service.StaticResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import java.net.URISyntaxException;
import java.util.List;

/**
 *
 * @author hxh
 * @date 2017-10-31
 */
@Controller
@RequestMapping("/forend/category")
public class WebCategoryController {
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private ProjectService projectService;
    @Autowired
    private StaticResourceService staticResourceService;
    @RequestMapping("/list")
    public String getCategoryList(Model model){
        List<LoanCategory> categoryList = categoryService.findAll();
        List<LoanProject> projectList = projectService.findAll();
        categoryList.forEach(p->{
            if(!StringUtils.isEmpty(p.getIcon())){
                try {
                    p.setIcon(staticResourceService.get(p.getIcon()).toString());
                } catch (URISyntaxException e) {
                }
            }
        });
        projectList.forEach(p->{
                    if(!StringUtils.isEmpty(p.getLogo())){
                        try {
                            p.setLogo(staticResourceService.get(p.getLogo()).toString());
                        } catch (URISyntaxException e) {
                        }
                    }
        });
        model.addAttribute("categoryList",categoryList);
        model.addAttribute("projectList",projectList);
        return "forend/loan";
    }
}

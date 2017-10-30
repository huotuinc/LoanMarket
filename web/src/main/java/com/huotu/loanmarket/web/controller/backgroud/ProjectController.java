package com.huotu.loanmarket.web.controller.backgroud;

import com.huotu.loanmarket.common.SysConstant;
import com.huotu.loanmarket.common.ienum.ApplicationMaterialEnum;
import com.huotu.loanmarket.service.entity.LoanCategory;
import com.huotu.loanmarket.service.entity.LoanProject;
import com.huotu.loanmarket.service.service.CategoryService;
import com.huotu.loanmarket.service.service.ProjectService;
import com.huotu.loanmarket.web.base.ApiResult;
import com.huotu.loanmarket.web.base.ResultCodeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.List;

/**
 * @author allan
 * @date 26/10/2017
 */
@Controller
@RequestMapping("/project")
public class ProjectController {
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private ProjectService projectService;

    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public String edit(
            @RequestParam(required = false, defaultValue = "0") int projectId,
            Model model
    ) {
        //分类
        List<LoanCategory> categories = categoryService.findAll();
        //申请材料
        ApplicationMaterialEnum[] applicationMaterials = ApplicationMaterialEnum.values();

        model.addAttribute("categories", categories);
        model.addAttribute("applicationMaterials", applicationMaterials);
        LoanProject project = projectService.findOne(projectId);
        if (project == null) {
            project = new LoanProject();
        }

        model.addAttribute("project", project);

        return "project_edit";
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(
            @RequestParam(required = false, defaultValue = "0") int pageIndex,
            Model model
    ) {
        List<LoanCategory> categories = categoryService.findAll();
        Page<LoanProject> projectPage = projectService.findAll(pageIndex, SysConstant.BACKEND_DEFALUT_PAGE_SIZE, null);

        model.addAttribute("categories", categories);
        model.addAttribute("pageIndex", pageIndex);
        model.addAttribute("totalRecord", projectPage.getTotalElements());
        model.addAttribute("projects", projectPage.getContent());

        return "project_list";
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    @ResponseBody
    public ApiResult edit(LoanProject project) {
        if (project.getLoanId() == null || project.getLoanId() == 0) {
            project.setCreateTime(new Date());
        }
        String[] enableMoneyArray = project.getEnableMoney().split(",");
        project.setMaxMoney(Double.parseDouble(enableMoneyArray[enableMoneyArray.length - 1]));
        projectService.save(project);
        return ApiResult.resultWith(ResultCodeEnum.SUCCESS);
    }
}

/*
 *   ~ 版权所有:杭州火图科技有限公司
 *   ~ 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *   ~
 *   ~ (c) Copyright Hangzhou Hot Technology Co., Ltd.
 *   ~ Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 *   ~ 2017-2020. All rights reserved.
 */

package com.huotu.loanmarket.web.controller.backgroud;

import com.huotu.loanmarket.common.SysConstant;
import com.huotu.loanmarket.common.enums.ApplicationMaterialEnum;
import com.huotu.loanmarket.service.entity.Category.Category;
import com.huotu.loanmarket.service.entity.project.Project;
import com.huotu.loanmarket.service.searchable.ProjectSearchCondition;
import com.huotu.loanmarket.service.service.CategoryService;
import com.huotu.loanmarket.service.service.ProjectService;
import com.huotu.loanmarket.web.base.ApiResult;
import com.huotu.loanmarket.web.base.ResultCodeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

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
        List<Category> categories = categoryService.findAll();
        //申请材料
        ApplicationMaterialEnum[] applicationMaterials = ApplicationMaterialEnum.values();

        model.addAttribute("categories", categories);
        model.addAttribute("applicationMaterials", applicationMaterials);
        Project project = projectService.findOne(projectId);
        if (project == null) {
            project = new Project();
        }
        model.addAttribute("project", project);
        return "project_edit";
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(
            @RequestParam(required = false, defaultValue = "1") int pageIndex,
            @ModelAttribute(value = "searchCondition") ProjectSearchCondition searchCondition,
            Model model
    ) {
        List<Category> categories = categoryService.findAll();
        Page<Project> projectPage = projectService.findAll(pageIndex, SysConstant.BACKEND_DEFALUT_PAGE_SIZE, searchCondition);

        projectPage.getContent().forEach(project -> {
            if(!StringUtils.isEmpty(project.getDeadline())){
                String[] deadlineArray = project.getDeadline().split(",");
                project.setMinDeadline(Integer.parseInt(deadlineArray[0]));
                project.setMaxDeadline(Integer.parseInt(deadlineArray[deadlineArray.length - 1]));
                switch (project.getDeadlineUnit()) {
                    case 0:
                        project.setDeadlineUnitDesc("天");
                        break;
                    case 1:
                        project.setDeadlineUnitDesc("月");
                        break;
                    case 2:
                        project.setDeadlineUnitDesc("年");
                        break;
                    default:
                        break;
                }
            }else {
                project.setDeadlineUnitDesc("天");
            }
        });

        model.addAttribute("categories", categories);
        model.addAttribute("pageIndex", pageIndex);
        model.addAttribute("totalRecord", projectPage.getTotalElements());
        model.addAttribute("totalPage", projectPage.getTotalPages());
        model.addAttribute("projects", projectPage.getContent());

        return "project_list";
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public ApiResult edit(Project project) {
        if (project.getLoanId() == null || project.getLoanId() == 0) {
            project.setCreateTime(new Date());
        }
        if (!StringUtils.isEmpty(project.getEnableMoney())){
            String[] enableMoneyArray = project.getEnableMoney().split(",");
            project.setMaxMoney(Double.parseDouble(enableMoneyArray[enableMoneyArray.length - 1]));
            project.setMinMoney(Double.parseDouble(enableMoneyArray[0]));
        }
        projectService.save(project);
        return ApiResult.resultWith(ResultCodeEnum.SUCCESS);
    }

    /**
     * 设置成删除
     *
     * @param projectId
     * @return
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public ApiResult delete(int projectId) {
        Project project = projectService.findOne(projectId);
        project.setIsDelete(1);
        projectService.save(project);

        return ApiResult.resultWith(ResultCodeEnum.SUCCESS);
    }

    @RequestMapping(value = "/setHot", method = RequestMethod.POST)
    @ResponseBody
    public ApiResult setHot(int isHot, String projectIdsStr) {
        projectService.setHot(isHot, projectIdsStr);
        return ApiResult.resultWith(ResultCodeEnum.SUCCESS);
    }

    @RequestMapping(value = "/setNew", method = RequestMethod.POST)
    @ResponseBody
    public ApiResult setNew(int isNew, String projectIdsStr) {
        projectService.setNew(isNew, projectIdsStr);
        return ApiResult.resultWith(ResultCodeEnum.SUCCESS);
    }
}

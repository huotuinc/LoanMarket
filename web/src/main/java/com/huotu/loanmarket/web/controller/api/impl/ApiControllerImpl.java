package com.huotu.loanmarket.web.controller.api.impl;


import com.huotu.loanmarket.service.entity.LoanCategory;
import com.huotu.loanmarket.service.entity.LoanProject;
import com.huotu.loanmarket.service.searchable.ProjectSearchCondition;
import com.huotu.loanmarket.service.service.*;
import com.huotu.loanmarket.web.base.ApiResult;
import com.huotu.loanmarket.web.base.ResultCodeEnum;
import com.huotu.loanmarket.web.controller.api.ApiController;
import com.huotu.loanmarket.web.service.StaticResourceService;
import com.huotu.loanmarket.web.viewmodel.ProjectIndexViewModel;
import com.huotu.loanmarket.web.viewmodel.ProjectListViewModel;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.net.URISyntaxException;
import java.util.List;

/**
 * @author allan
 * @date 23/10/2017
 */
@Controller
@RequestMapping(value = "/rest/api", method = RequestMethod.POST)
public class ApiControllerImpl implements ApiController {
    private static final Log log = LogFactory.getLog(ApiControllerImpl.class);

    @Autowired
    private ProjectService projectService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private ApplyLogService applyLogService;
    @Autowired
    private ViewLogService viewLogService;
    @Autowired
    private StaticResourceService staticResourceService;


    @Override
    @RequestMapping(value = "/project/list", method = RequestMethod.POST)
    @ResponseBody
    public ApiResult projectList(
            @RequestParam(required = false, defaultValue = "1") int pageIndex,
            @RequestParam(required = false, defaultValue = "10") int pageSize,
            ProjectSearchCondition projectSearchCondition
    ) {
        Page<LoanProject> projectPage = projectService.findAll(pageIndex, pageSize, projectSearchCondition);

        ProjectListViewModel projectListViewModel = new ProjectListViewModel();
        projectListViewModel.toApiProjectList(projectPage);
        projectListViewModel.getList().forEach(p -> {
            if (!StringUtils.isEmpty(p.getLogo())) {
                try {
                    p.setLogo(staticResourceService.get(p.getLogo()).toString());
                } catch (URISyntaxException e) {
                    log.error("获取图片资源异常：" + e);
                }
            }
            if (!StringUtils.isEmpty(p.getTag()) && p.getTag().split(",").length > 3) {
                String[] tags = p.getTag().split(",");
                String tag = tags[0] + "," + tags[1] + "," + tags[2];
                p.setTag(tag);
            }
        });
        return ApiResult.resultWith(ResultCodeEnum.SUCCESS, projectListViewModel);
    }

    @Override
    @RequestMapping("/project/categories")
    @ResponseBody
    public ApiResult projectCategory() {
        List<LoanCategory> categoryList = categoryService.findAll();
        categoryList.forEach(p -> {
            if (!StringUtils.isEmpty(p.getIcon())) {
                try {
                    p.setIcon(staticResourceService.get(p.getIcon()).toString());
                } catch (URISyntaxException e) {
                    log.error("获取图片资源异常：" + e);
                }
            }
        });
        return ApiResult.resultWith(ResultCodeEnum.SUCCESS, categoryList);
    }

    @Override
    @RequestMapping("/project/detail")
    @ResponseBody
    public ApiResult projectDetail(
            int projectId,
            @RequestParam(required = false, defaultValue = "0") int userId
    ) {
        //获取project
        LoanProject project = projectService.findOne(projectId);
        if (!StringUtils.isEmpty(project.getLogo())) {
            try {
                project.setLogo(staticResourceService.get(project.getLogo()).toString());
            } catch (URISyntaxException e) {
                log.error("获取图片资源异常：" + e);
            }
        }
        if (!StringUtils.isEmpty(project.getTag()) && project.getTag().split(",").length > 3) {
            String[] tags = project.getTag().split(",");
            String tag = tags[0] + "," + tags[1] + "," + tags[2];
            project.setTag(tag);
        }
        if (userId > 0) {
            //记录日志  注：这是两个完全不同的业务没必要封装在同一个方法中
            viewLogService.log(userId, projectId);
        }

        return ApiResult.resultWith(ResultCodeEnum.SUCCESS, project);
    }

    @Override
    @RequestMapping("/project/applyLog")
    @ResponseBody
    public ApiResult applyLog(Long userId, int projectId) {
        applyLogService.log(userId, projectId);
        return ApiResult.resultWith(ResultCodeEnum.SUCCESS);
    }

    @Override
    @RequestMapping("/project/index")
    @ResponseBody
    public ApiResult projectIndex() {
        ProjectIndexViewModel model = new ProjectIndexViewModel();
        List<LoanProject> hotList = projectService.getHotProject();
        List<LoanProject> newList = projectService.getNewProject();
        hotList.forEach(p -> {
            if (!StringUtils.isEmpty(p.getLogo())) {
                try {
                    p.setLogo(staticResourceService.get(p.getLogo()).toString());
                } catch (URISyntaxException e) {
                    log.error("获取图片资源异常：" + e);
                }
            }
            if (!StringUtils.isEmpty(p.getTag()) && p.getTag().split(",").length > 3) {
                String[] tags = p.getTag().split(",");
                String tag = tags[0] + "," + tags[1] + "," + tags[2];
                p.setTag(tag);
            }
        });
        newList.forEach(project -> {
            if (!StringUtils.isEmpty(project.getLogo())) {
                try {
                    project.setLogo(staticResourceService.get(project.getLogo()).toString());
                } catch (URISyntaxException e) {
                    log.error("获取图片资源异常：" + e);
                }
            }
            if (!StringUtils.isEmpty(project.getTag()) && project.getTag().split(",").length > 3) {
                String[] tags = project.getTag().split(",");
                String tag = tags[0] + "," + tags[1] + "," + tags[2];
                project.setTag(tag);
            }
        });
        model.setHotProjectList(hotList);
        model.setNewProjectList(newList);
        return ApiResult.resultWith(ResultCodeEnum.SUCCESS, model);
    }

}

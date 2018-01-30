package com.huotu.loanmarket.webapi.controller.project;

import com.huotu.loanmarket.common.utils.ApiResult;
import com.huotu.loanmarket.service.entity.category.Category;
import com.huotu.loanmarket.service.entity.project.Project;
import com.huotu.loanmarket.service.enums.AppCode;
import com.huotu.loanmarket.service.model.ProjectIndexViewModel;
import com.huotu.loanmarket.service.model.ProjectListViewModel;
import com.huotu.loanmarket.service.model.projectsearch.ProjectSearchCondition;
import com.huotu.loanmarket.service.service.category.CategoryService;
import com.huotu.loanmarket.service.service.project.ProjectService;
import com.huotu.loanmarket.webapi.service.StaticResourceService;
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
 * @Author hxh
 * @Date 2018/1/30 14:41
 */
@Controller
@RequestMapping("/api/project")
public class ProjectController {
    @Autowired
    private ProjectService projectService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private StaticResourceService staticResourceService;

    @RequestMapping("/detail")
    @ResponseBody
    public ApiResult projectDetail(
            int projectId,
            @RequestParam(required = false, defaultValue = "0") int userId
    ) {
        //获取project
        Project project = projectService.findOne(projectId);
        if (!StringUtils.isEmpty(project.getLogo())) {
            try {
                project.setLogo(staticResourceService.get(project.getLogo()).toString());
            } catch (URISyntaxException e) {
            }
        }
        if (!StringUtils.isEmpty(project.getTag()) && project.getTag().split(",").length > 3) {
            String[] tags = project.getTag().split(",");
            String tag = tags[0] + "," + tags[1] + "," + tags[2];
            project.setTag(tag);
        }
        if (userId > 0) {
            //记录日志  注：这是两个完全不同的业务没必要封装在同一个方法中
            projectService.logView(userId, projectId);
        }

        return ApiResult.resultWith(AppCode.SUCCESS, project);
    }

    @RequestMapping("/categories")
    @ResponseBody
    public ApiResult projectCategory() {
        List<Category> categoryList = categoryService.findAll();
        categoryList.forEach(p -> {
            if (!StringUtils.isEmpty(p.getIcon())) {
                try {
                    p.setIcon(staticResourceService.get(p.getIcon()).toString());
                } catch (URISyntaxException e) {
                }
            }
        });
        return ApiResult.resultWith(AppCode.SUCCESS, categoryList);
    }

    @RequestMapping(value = "/project/list", method = RequestMethod.POST)
    @ResponseBody
    public ApiResult projectList(
            @RequestParam(required = false, defaultValue = "1") int pageIndex,
            @RequestParam(required = false, defaultValue = "10") int pageSize,
            ProjectSearchCondition projectSearchCondition
    ) {
        Page<Project> projectPage = projectService.findAll(pageIndex, pageSize, projectSearchCondition);

        ProjectListViewModel projectListViewModel = new ProjectListViewModel();
        projectListViewModel.toApiProjectList(projectPage);
        projectListViewModel.getList().forEach(p -> {
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
        return ApiResult.resultWith(AppCode.SUCCESS, projectListViewModel);
    }

    @RequestMapping("/applyLog")
    @ResponseBody
    public ApiResult applyLog(int userId, int projectId) {
        projectService.logApply(userId, projectId);
        return ApiResult.resultWith(AppCode.SUCCESS);
    }

    @RequestMapping("/project/index")
    @ResponseBody
    public ApiResult projectIndex() {
        ProjectIndexViewModel model = new ProjectIndexViewModel();
        List<Project> hotList = projectService.getHotProject();
        List<Project> newList = projectService.getNewProject();
        hotList.forEach(p -> {
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
        newList.forEach(project -> {
            if (!StringUtils.isEmpty(project.getLogo())) {
                try {
                    project.setLogo(staticResourceService.get(project.getLogo()).toString());
                } catch (URISyntaxException e) {
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
        return ApiResult.resultWith(AppCode.SUCCESS, model);
    }
}

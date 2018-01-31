package com.huotu.loanmarket.webapi.controller.project;

import com.huotu.loanmarket.common.Constant;
import com.huotu.loanmarket.common.utils.ApiResult;
import com.huotu.loanmarket.service.entity.category.Category;
import com.huotu.loanmarket.service.entity.project.Project;
import com.huotu.loanmarket.service.enums.AppCode;
import com.huotu.loanmarket.service.model.ProjectIndexViewModel;
import com.huotu.loanmarket.service.model.ProjectListViewModel;
import com.huotu.loanmarket.service.model.project.ProjectVo;
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
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author hxh
 * @Date 2018/1/30 14:41
 */
@Controller
@RequestMapping(value = "/api/project", method = RequestMethod.POST)
public class ProjectController {
    @Autowired
    private ProjectService projectService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private StaticResourceService staticResourceService;

    /**
     * 贷款产品详情接口
     *
     * @param projectId
     * @param userId
     * @return
     */
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

    /**
     * 贷款分类接口
     *
     * @return
     */
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
        LinkedHashMap<String, Object> map = new LinkedHashMap<>();
        map.put("list", categoryList);
        return ApiResult.resultWith(AppCode.SUCCESS, map);
    }

    /**
     * 贷款成品列表接口
     *
     * @param pageIndex
     * @param pageSize
     * @param categoryId 分类ID
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ResponseBody
    public ApiResult projectList(
            @RequestParam(required = false, defaultValue = "1") int pageIndex,
            @RequestParam(required = false, defaultValue = Constant.PAGE_SIZE_STR) int pageSize,
            @RequestParam(required = false, defaultValue = "0") int categoryId
    ) {
        pageIndex = pageIndex <= 0 ? 1 : pageIndex;
        ProjectSearchCondition searchCondition = new ProjectSearchCondition();
        searchCondition.setCategoryId(categoryId);
        Page<Project> projectPage = projectService.findAll(pageIndex, pageSize, searchCondition);
        List<Project> list = projectPage.getContent();

        List<ProjectVo> result = new ArrayList<>();
        list.forEach(p -> {
            ProjectVo projectVo = new ProjectVo();
            projectVo.setLoanId(p.getLoanId());
            projectVo.setApplyType(p.getApplyType());
            projectVo.setApplyUrl(p.getApplyUrl());
            projectVo.setDeadlineUnit(p.getDeadlineUnit());
            projectVo.setInterestRate(p.getInterestRate());
            projectVo.setIsHot(p.getIsHot());
            projectVo.setIsNew(p.getIsNew());
            projectVo.setMaxMoney(p.getMaxMoney());
            projectVo.setMinMoney(p.getMinMoney());
            projectVo.setName(p.getName());

            if (!StringUtils.isEmpty(p.getLogo())) {
                try {
                    projectVo.setLogo(staticResourceService.get(p.getLogo()).toString());
                } catch (URISyntaxException e) {
                }
            }
            if (!StringUtils.isEmpty(p.getTag()) && p.getTag().split(",").length > 3) {
                String[] tags = p.getTag().split(",");
                String tag = tags[0] + "," + tags[1] + "," + tags[2];
                projectVo.setTag(tag);
            }
            result.add(projectVo);
        });
        LinkedHashMap<String, Object> map = new LinkedHashMap<>();
        map.put("list", result);
        return ApiResult.resultWith(AppCode.SUCCESS, map);
    }

    @RequestMapping("/applyLog")
    @ResponseBody
    public ApiResult applyLog(int userId, int projectId) {
        projectService.logApply(userId, projectId);
        return ApiResult.resultWith(AppCode.SUCCESS);
    }

    /**
     * 首页接口
     *
     * @return
     */
    @RequestMapping("/index")
    @ResponseBody
    public ApiResult projectIndex() {
        List<Project> hotList = projectService.getHotProject();
        List<Project> newList = projectService.getNewProject();

        List<ProjectVo> resultHot = new ArrayList<>();
        List<ProjectVo> resultNew = new ArrayList<>();
        hotList.forEach(p -> {

            ProjectVo projectVo = new ProjectVo();
            projectVo.setLoanId(p.getLoanId());
            projectVo.setApplyType(p.getApplyType());
            projectVo.setApplyUrl(p.getApplyUrl());
            projectVo.setDeadlineUnit(p.getDeadlineUnit());
            projectVo.setInterestRate(p.getInterestRate());
            projectVo.setIsHot(p.getIsHot());
            projectVo.setIsNew(p.getIsNew());
            projectVo.setMaxMoney(p.getMaxMoney());
            projectVo.setMinMoney(p.getMinMoney());
            projectVo.setName(p.getName());

            if (!StringUtils.isEmpty(p.getLogo())) {
                try {
                    projectVo.setLogo(staticResourceService.get(p.getLogo()).toString());
                } catch (URISyntaxException e) {
                }
            }
            if (!StringUtils.isEmpty(p.getTag()) && p.getTag().split(",").length > 3) {
                String[] tags = p.getTag().split(",");
                projectVo.setTag(tags[0] + "," + tags[1] + "," + tags[2]);
            }
            resultHot.add(projectVo);
        });
        //
        newList.forEach(p -> {
            ProjectVo projectVo = new ProjectVo();
            projectVo.setLoanId(p.getLoanId());
            projectVo.setApplyType(p.getApplyType());
            projectVo.setApplyUrl(p.getApplyUrl());
            projectVo.setDeadlineUnit(p.getDeadlineUnit());
            projectVo.setInterestRate(p.getInterestRate());
            projectVo.setIsHot(p.getIsHot());
            projectVo.setIsNew(p.getIsNew());
            projectVo.setMaxMoney(p.getMaxMoney());
            projectVo.setMinMoney(p.getMinMoney());
            projectVo.setName(p.getName());
            if (!StringUtils.isEmpty(p.getLogo())) {
                try {
                    projectVo.setLogo(staticResourceService.get(p.getLogo()).toString());
                } catch (URISyntaxException e) {
                }
            }
            if (!StringUtils.isEmpty(p.getTag()) && p.getTag().split(",").length > 3) {
                projectVo.setTag(getTag(p));
            }
            resultNew.add(projectVo);
        });
        LinkedHashMap<String, Object> map = new LinkedHashMap<>();
        map.put("hotProjectList", resultHot);
        map.put("newProjectList", resultNew);
        return ApiResult.resultWith(AppCode.SUCCESS, map);
    }

    private String getTag(Project project) {
        if (project.getTag().split(",").length > 3) {
            String[] tags = project.getTag().split(",");
            return tags[0] + "," + tags[1] + "," + tags[2];
        }
        return "";
    }

}

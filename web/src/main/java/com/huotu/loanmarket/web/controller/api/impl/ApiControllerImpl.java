package com.huotu.loanmarket.web.controller.api.impl;

import com.huotu.loanmarket.service.entity.LoanCategory;
import com.huotu.loanmarket.service.entity.LoanEquipment;
import com.huotu.loanmarket.service.entity.LoanProject;
import com.huotu.loanmarket.service.entity.LoanUser;
import com.huotu.loanmarket.service.searchable.ProjectSearchCondition;
import com.huotu.loanmarket.service.service.ApplyLogService;
import com.huotu.loanmarket.service.service.CategoryService;
import com.huotu.loanmarket.service.service.EquipmentService;
import com.huotu.loanmarket.service.service.ProjectService;
import com.huotu.loanmarket.service.service.UserService;
import com.huotu.loanmarket.web.common.ApiResult;
import com.huotu.loanmarket.web.common.ResultCodeEnum;
import com.huotu.loanmarket.web.controller.api.ApiController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author allan
 * @date 23/10/2017
 */
@Controller
@RequestMapping("/rest/api")
public class ApiControllerImpl implements ApiController {
    @Autowired
    private ProjectService projectService;
    @Autowired
    private EquipmentService equipmentService;
    @Autowired
    private UserService userService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private ApplyLogService applyLogService;

    @Override
    public ApiResult appInfo(String appVersion, String osVersion, String osType) {
        LoanEquipment loanEquipment = equipmentService.saveAppInfo(appVersion, osVersion, osType);
        if (loanEquipment != null) {
            return ApiResult.resultWith(ResultCodeEnum.SUCCESS);
        }
        return ApiResult.resultWith(ResultCodeEnum.SYSTEM_BAD_REQUEST);
    }

    @Override
    public ApiResult init(int userId) {
        //如果用户未登录返回null，登录返回用户信息
        if (userId > 0) {
            return ApiResult.resultWith(ResultCodeEnum.SUCCESS, userService.findOne(userId));
        }
        return ApiResult.resultWith(ResultCodeEnum.SYSTEM_BAD_REQUEST);
    }

    @Override
    @RequestMapping(value = "/project/list", method = RequestMethod.POST)
    @ResponseBody
    public ApiResult projectList(ProjectSearchCondition projectSearchCondition) {
        List<LoanProject> projectList = projectService.getProjectList(projectSearchCondition);
        return ApiResult.resultWith(ResultCodeEnum.SUCCESS, projectList);
    }

    @Override
    public ApiResult projectCategory() {
        List<LoanCategory> categoryList = categoryService.getAll();
        return ApiResult.resultWith(ResultCodeEnum.SUCCESS, categoryList);
    }

    @Override
    public ApiResult projectDetail(int projectId, int userId) {
        LoanProject project = projectService.getProjectDetail(projectId, userId);
        return ApiResult.resultWith(ResultCodeEnum.SUCCESS, project);
    }

    @Override
    @RequestMapping(value = "/user/login", method = RequestMethod.POST)
    @ResponseBody
    public ApiResult login(String mobile, String verifyCode) {
        LoanUser user = userService.checkLogin(mobile, verifyCode);
        if (user != null) {
            return ApiResult.resultWith(ResultCodeEnum.SUCCESS, user);
        }
        return ApiResult.resultWith(ResultCodeEnum.SYSTEM_BAD_REQUEST);
    }

    @Override
    public ApiResult applyCount(int userId, int projectId) {
        boolean count = applyLogService.applyCount(userId, projectId);
        if (count) {
            return ApiResult.resultWith(ResultCodeEnum.SUCCESS);
        }
        return ApiResult.resultWith(ResultCodeEnum.SYSTEM_BAD_REQUEST);
    }

    @RequestMapping("/test")
    @ResponseBody
    public ApiResult test() {
        LoanProject project = new LoanProject();
        project.setName("hahatest");
        projectService.save(project);
        return ApiResult.resultWith(ResultCodeEnum.SUCCESS, project);
    }
}

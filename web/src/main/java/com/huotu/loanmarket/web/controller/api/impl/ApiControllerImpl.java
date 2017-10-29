package com.huotu.loanmarket.web.controller.api.impl;

import com.huotu.loanmarket.service.entity.LoanCategory;
import com.huotu.loanmarket.service.entity.LoanEquipment;
import com.huotu.loanmarket.service.entity.LoanProject;
import com.huotu.loanmarket.service.entity.LoanUser;
import com.huotu.loanmarket.service.searchable.ProjectSearchCondition;
import com.huotu.loanmarket.service.service.*;
import com.huotu.loanmarket.web.base.ApiResult;
import com.huotu.loanmarket.web.base.ResultCodeEnum;
import com.huotu.loanmarket.web.controller.api.ApiController;
import com.huotu.loanmarket.web.viewmodel.ProjectListViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author allan
 * @date 23/10/2017
 */
@Controller
@RequestMapping(value = "/rest/api", method = RequestMethod.POST)
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
    @Autowired
    private ViewLogService viewLogService;

    @Override
    @RequestMapping("/appInfo")
    @ResponseBody
    public ApiResult appInfo(String appVersion, String osVersion, String osType) {
        LoanEquipment loanEquipment = equipmentService.saveAppInfo(appVersion, osVersion, osType);
        if (loanEquipment != null) {
            return ApiResult.resultWith(ResultCodeEnum.SUCCESS);
        }
        return ApiResult.resultWith(ResultCodeEnum.SYSTEM_BAD_REQUEST);
    }

    @Override
    @RequestMapping("/user/detail")
    @ResponseBody
    public ApiResult userDetail(@RequestParam(required = false, defaultValue = "0") int userId) {
        //如果用户未登录返回null，登录返回用户信息
        if (userId > 0) {
            return ApiResult.resultWith(ResultCodeEnum.SUCCESS, userService.findOne(userId));
        }
        return ApiResult.resultWith(ResultCodeEnum.SYSTEM_BAD_REQUEST, "用户未登录", null);
    }

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

        return ApiResult.resultWith(ResultCodeEnum.SUCCESS, projectListViewModel);
    }

    @Override
    @RequestMapping("/project/categories")
    @ResponseBody
    public ApiResult projectCategory() {
        List<LoanCategory> categoryList = categoryService.getAll();
        return ApiResult.resultWith(ResultCodeEnum.SUCCESS, categoryList);
    }

//    @Override
//    public ApiResult projectTopList(ProjectSearchTopCondition projectSearchTopCondition) {
//        List<LoanProject> projectTopList = projectService.getProjectTopList(projectSearchTopCondition);
//        return ApiResult.resultWith(ResultCodeEnum.SUCCESS, projectTopList);
//    }

    @Override
    @RequestMapping("/project/detail")
    @ResponseBody
    public ApiResult projectDetail(
            int projectId,
            @RequestParam(required = false, defaultValue = "0") int userId
    ) {
        //获取project
        LoanProject project = projectService.findOne(projectId);

        if (userId > 0) {
            //记录日志  注：这是两个完全不同的业务没必要封装在同一个方法中
            viewLogService.log(userId, projectId);
        }

        return ApiResult.resultWith(ResultCodeEnum.SUCCESS, project);
    }

    @Override
    @RequestMapping(value = "/user/login")
    @ResponseBody
    public ApiResult login(String mobile, String verifyCode) {
        LoanUser user = userService.checkLogin(mobile, verifyCode);
        if (user != null) {
            return ApiResult.resultWith(ResultCodeEnum.SUCCESS, user);
        }
        return ApiResult.resultWith(ResultCodeEnum.SYSTEM_BAD_REQUEST);
    }

    @Override
    @RequestMapping("/project/applyLog")
    @ResponseBody
    public ApiResult applyLog(int userId, int projectId) {
        applyLogService.log(userId, projectId);

        return ApiResult.resultWith(ResultCodeEnum.SUCCESS);
    }
}

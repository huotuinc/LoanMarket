package com.huotu.loanmarket.web.controller.api.impl;

import com.huotu.loanmarket.service.entity.LoanApplyLog;
import com.huotu.loanmarket.service.entity.LoanCategory;
import com.huotu.loanmarket.service.entity.LoanEquipment;
import com.huotu.loanmarket.service.entity.LoanProject;
import com.huotu.loanmarket.service.entity.LoanUser;
import com.huotu.loanmarket.service.searchable.ProjectSearchCondition;
import com.huotu.loanmarket.service.service.ApiService;
import com.huotu.loanmarket.web.common.ApiResult;
import com.huotu.loanmarket.web.common.ResultCodeEnum;
import com.huotu.loanmarket.web.controller.api.ApiController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @author allan
 * @date 23/10/2017
 */
@Controller
@RequestMapping("/rest/api")
public class ApiControllerImpl implements ApiController {
    @Autowired
    private ApiService apiService;


    @Override
    public ApiResult appInfo(String appVersion, String osVersion, String osType) {
        LoanEquipment loanEquipment = apiService.saveAppInfo(appVersion, osVersion, osType);
        if (loanEquipment != null) {
            return ApiResult.resultWith(ResultCodeEnum.SUCCESS);
        }
        return ApiResult.resultWith(ResultCodeEnum.SYSTEM_BAD_REQUEST);
    }

    @Override
    public ApiResult init(int userId) {
        //如果用户未登录返回null，登录返回用户信息
        LoanUser user = apiService.init(userId);
        if (user != null) {
            return ApiResult.resultWith(ResultCodeEnum.SUCCESS, user);
        }
        return null;
    }

    @Override
    public ApiResult projectList(ProjectSearchCondition projectSearchCondition) {
        List<LoanProject> projectList = apiService.getProjectList(projectSearchCondition);
        return ApiResult.resultWith(ResultCodeEnum.SUCCESS, projectList);
    }

    @Override
    public ApiResult projectCategory() {
        List<LoanCategory> projectCategory = apiService.getProjectCategory();
        return ApiResult.resultWith(ResultCodeEnum.SUCCESS, projectCategory);
    }

    @Override
    public ApiResult projectDetail(int projectId, int userId) {
        LoanProject loanProject = apiService.getProjectDetail(projectId, userId);
        return ApiResult.resultWith(ResultCodeEnum.SUCCESS, loanProject);
    }

    @Override
    public ApiResult login(String mobile, String verifyCode) {
        apiService.checkLogin(mobile, verifyCode);
        return ApiResult.resultWith(ResultCodeEnum.SUCCESS);
    }

    @Override
    public ApiResult applyCount(int userId, int projectId) {
        LoanApplyLog loanApplyLog = apiService.applyCount(userId, projectId);
        if (loanApplyLog != null) {
            return ApiResult.resultWith(ResultCodeEnum.SUCCESS);
        }
        return ApiResult.resultWith(ResultCodeEnum.SYSTEM_BAD_REQUEST);
    }
}

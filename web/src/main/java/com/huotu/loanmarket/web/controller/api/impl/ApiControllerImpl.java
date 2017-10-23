package com.huotu.loanmarket.web.controller.api.impl;

import com.huotu.loanmarket.service.searchable.ProjectSearchCondition;
import com.huotu.loanmarket.web.common.ApiResult;
import com.huotu.loanmarket.web.controller.api.ApiController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author allan
 * @date 23/10/2017
 */
@Controller
@RequestMapping("/rest/api")
public class ApiControllerImpl implements ApiController {
    @Override
    public ApiResult appInfo(String appVersion, String osVersion, String osType) {
        return null;
    }

    @Override
    public ApiResult init() {
        return null;
    }

    @Override
    public ApiResult projectList(ProjectSearchCondition projectSearchCondition) {
        return null;
    }

    @Override
    public ApiResult projectCategory() {
        return null;
    }

    @Override
    public ApiResult projectDetail(int projectId, int userId) {
        return null;
    }

    @Override
    public ApiResult login(String mobile, String verifyCode) {
        return null;
    }

    @Override
    public ApiResult applyCount(int userId, int projectId) {
        return null;
    }
}

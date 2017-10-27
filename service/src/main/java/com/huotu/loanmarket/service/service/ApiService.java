package com.huotu.loanmarket.service.service;

import com.huotu.loanmarket.service.entity.LoanApplyLog;
import com.huotu.loanmarket.service.entity.LoanCategory;
import com.huotu.loanmarket.service.entity.LoanEquipment;
import com.huotu.loanmarket.service.entity.LoanProject;
import com.huotu.loanmarket.service.entity.LoanUser;
import com.huotu.loanmarket.service.searchable.ProjectSearchCondition;

import java.util.List;

/**
 * @author hxh
 * @date 2017-10-27
 */
public interface ApiService {
    /**
     * 保存设备信息
     *
     * @param appVersion
     * @param osVersion
     * @param osType
     * @return
     */
    LoanEquipment saveAppInfo(String appVersion, String osVersion, String osType);

    /**
     * 初始化
     *
     * @param userId
     * @return
     */
    LoanUser init(int userId);

    /**
     * 根据条件查询产品列表
     * @param projectSearchCondition
     * @return
     */
    List<LoanProject> getProjectList(ProjectSearchCondition projectSearchCondition);

    /**
     * 获取所有类目
     * @return
     */
    List<LoanCategory> getProjectCategory();

    /**
     * 获取产品详情
     * @return
     */
    LoanProject getProjectDetail(int projectId, int userId);

    /**
     * 检查登录
     * @param mobile
     * @param verifyCode
     * @return
     */
    boolean checkLogin(String mobile, String verifyCode);

    /**
     * 记录申请量
     * @param userId
     * @param projectId
     */
    LoanApplyLog applyCount(int userId, int projectId);

}

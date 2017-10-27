package com.huotu.loanmarket.web.controller.api;

import com.huotu.loanmarket.service.entity.LoanProject;
import com.huotu.loanmarket.service.searchable.ProjectSearchCondition;
import com.huotu.loanmarket.web.common.ApiResult;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author allan
 * @date 23/10/2017
 */
@Controller
@RequestMapping("/rest/api")
public interface ApiController {
    /**
     * 接收设备信息
     *
     * @param appVersion app版本
     * @param osVersion  手机操作系统
     * @param osType     操作类型
     * @return
     */
    @RequestMapping("/appInfo")
    @ResponseBody
    ApiResult appInfo(
            String appVersion,
            String osVersion,
            String osType
    );

    /**
     * 初始化
     *
     * @param userId
     * @return
     */
    @RequestMapping("/init")
    @ResponseBody
    ApiResult init(int userId);

    /**
     * 分组按条件搜索产品列表
     *
     * @param projectSearchCondition 搜索条件, {@link ProjectSearchCondition}
     * @return {@link LoanProject}
     */
    @RequestMapping("/project/list")
    @ResponseBody
    ApiResult projectList(ProjectSearchCondition projectSearchCondition);

    /**
     * 获得所有分类
     *
     * @return
     */
    @RequestMapping("/project/category")
    @ResponseBody
    ApiResult projectCategory();

    /**
     * 返回产品详情，同时记录浏览量，未登录不记录
     *
     * @param projectId 产品id
     * @param userId    登录用于id，0表示未登录
     * @return
     */
    @RequestMapping("/project/detail")
    @ResponseBody
    ApiResult projectDetail(
            int projectId,
            int userId
    );

    /**
     * 登录
     *
     * @param mobile     手机号
     * @param verifyCode 验证码
     * @return
     */
    @RequestMapping("/user/login")
    @ResponseBody
    ApiResult login(String mobile, String verifyCode);

    /**
     * 记录申请量
     *
     * @param userId    用户id
     * @param projectId 项目id
     * @return
     */
    @RequestMapping("/project/applyCount")
    @ResponseBody
    ApiResult applyCount(int userId, int projectId);
}

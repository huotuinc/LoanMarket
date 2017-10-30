package com.huotu.loanmarket.web.controller.api;

import com.huotu.loanmarket.service.searchable.ProjectSearchCondition;
import com.huotu.loanmarket.web.base.ApiResult;
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
     * 初始化：设备信息，用户登录返回用户信息
     *
     * @param userId
     * @param appVersion
     * @param osType
     * @param osVersion
     * @return
     */
    @RequestMapping("//user/init")
    @ResponseBody
    ApiResult userDetail(int userId,String appVersion,
                         String osVersion,
                         String osType);

    /**
     * 分组按条件搜索产品列表
     *
     * @param pageIndex              页码，索引从1开始
     * @param pageSize               每页数量，默认10
     * @param projectSearchCondition 搜索条件，{@link ProjectSearchCondition}
     * @return
     */
    @RequestMapping("/project/list")
    @ResponseBody
    ApiResult projectList(int pageIndex, int pageSize, ProjectSearchCondition projectSearchCondition);

    /**
     * 获得所有分类
     *
     * @return
     */
    @RequestMapping("/project/categories")
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
    @RequestMapping("/project/applyLog")
    @ResponseBody
    ApiResult applyLog(int userId, int projectId);
}

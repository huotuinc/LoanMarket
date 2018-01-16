package com.huotu.loanmarket.web.controller.api;

import com.huotu.loanmarket.service.searchable.ProjectSearchCondition;
import com.huotu.loanmarket.web.base.ApiResult;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;

/**
 * @author allan
 * @date 23/10/2017
 */
@Controller
@RequestMapping("/rest/api")
public interface ApiController {

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
     * 记录申请量
     *
     * @param userId    用户id
     * @param projectId 项目id
     * @return
     */
    @RequestMapping("/project/applyLog")
    @ResponseBody
    ApiResult applyLog(Long userId, int projectId);

    /**
     * 获取首页数据（返回最新和最热的产品数据，没有分页）
     *
     * @return
     */
    @RequestMapping("/project/index")
    @ResponseBody
    ApiResult projectIndex();

}

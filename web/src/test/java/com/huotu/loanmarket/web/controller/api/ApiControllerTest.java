package com.huotu.loanmarket.web.controller.api;

import com.huotu.loanmarket.service.service.ProjectService;
import com.huotu.loanmarket.web.base.ApiTestBase;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by hxh on 2017-10-26.
 */
public class ApiControllerTest extends ApiTestBase {

    @Autowired
    private ProjectService projectService;

    @Test
    public void appInfoTest() {
//        String appVersion = UUID.randomUUID().toString();
//        String osVersion = UUID.randomUUID().toString();
//        String osType = UUID.randomUUID().toString();
//        ApiResult apiResult = apiController.appInfo(appVersion, osVersion, osType);
//        Assert.assertTrue(apiResult.getResultCode() == 2000);
    }

    /**
     * 分组按条件搜索产品列表
     */
    @Test
    public void projectListTest() {
//        ProjectSearchCondition projectSearchCondition = new ProjectSearchCondition();
//        projectSearchCondition.setTopNum(1);
//        projectSearchCondition.setDesc(UUID.randomUUID().toString());
//        projectSearchCondition.setSid(1);
//        ApiResult apiResult = apiController.projectList(projectSearchCondition);
//        Assert.assertTrue(apiResult.getResultCode() == 2000);


    }

    /**
     * 获取所有分类
     */
    @Test
    public void projectCategoryTest() {
    }

    @Test
    public void projectDetailTest() {
    }

    @Test
    public void loginTest() {

    }

    @Test
    public void init() {

    }
}

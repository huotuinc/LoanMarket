package com.huotu.loanmarket.web.controller.api;

import com.huotu.loanmarket.service.searchable.ProjectSearchCondition;
import com.huotu.loanmarket.web.CommonTestBase;
import com.huotu.loanmarket.web.common.ApiResult;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

/**
 * Created by hxh on 2017-10-26.
 */
public class ApiControllerTest extends CommonTestBase {
    @Autowired
    private ApiController apiController;

    @Test
    public void appInfoTest() {
        String appVersion = UUID.randomUUID().toString();
        String osVersion = UUID.randomUUID().toString();
        String osType = UUID.randomUUID().toString();
        ApiResult apiResult = apiController.appInfo(appVersion, osVersion, osType);
        Assert.assertTrue(apiResult.getResultCode() == 2000);
    }

    /**
     * 分组按条件搜索产品列表
     */
    @Test
    public void projectListTest() {
        ProjectSearchCondition projectSearchCondition = new ProjectSearchCondition();
        projectSearchCondition.setTopNum(1);
        projectSearchCondition.setDesc(UUID.randomUUID().toString());
        projectSearchCondition.setSid(1);
        ApiResult apiResult = apiController.projectList(projectSearchCondition);
        Assert.assertTrue(apiResult.getResultCode() == 2000);
    }

    /**
     * 获取所有分类
     */
    @Test
    public void projectCategoryTest() {
        ApiResult apiResult = apiController.projectCategory();
        Assert.assertTrue(apiResult.getResultCode() == 2000);
    }

    @Test
    public void projectDetailTest() {
        ApiResult apiResult = apiController.projectDetail((int) Math.random() * 10 + 1, (int) Math.random() * 10 + 1);
        Assert.assertTrue(apiResult.getResultCode() == 2000);
    }

    @Test
    public void loginTest() {
        ApiResult result = apiController.login("15958039934", "123");
        Assert.assertTrue(result.getResultCode() == 2000);
    }

    @Test
    public void init() {
        ApiResult apiResult = apiController.init(1);
        Assert.assertTrue(apiResult.getResultCode() == 2000);
        //未登录
        ApiResult result = apiController.init(0);
        Assert.assertTrue(result==null);
    }
}

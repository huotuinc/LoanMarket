/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼在地图中查看
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2016-2018. All rights reserved.
 */

package com.huotu.loanmarket.webapi.controller.system;

import com.huotu.loanmarket.common.Constant;
import com.huotu.loanmarket.common.utils.RandomUtils;
import com.huotu.loanmarket.service.entity.user.User;
import com.huotu.loanmarket.service.enums.AppCode;
import com.huotu.loanmarket.service.service.system.AppVersionService;
import com.huotu.loanmarket.webapi.controller.base.BaseTest;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 *
 */
public class SystemControllerTest extends BaseTest {

    @Autowired
    private AppVersionService appVersionService;

    @Test
    public void init() throws Exception {
        User user = mockUser();
        mockMvc.perform(post("/api/sys/init")
                .header(Constant.APP_USER_ID_KEY, user.getUserId())
                .header(Constant.APP_USER_TOKEN_KEY,user.getUserToken())
                .header(Constant.APP_SYSTEM_TYPE_KEY, "android"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath(RESULT_CODE_PATH).value(AppCode.SUCCESS.getCode()));
    }

    @Test
    public void checkUpdate() throws Exception {

        //appVersionService.save();
        mockMvc.perform(post("/api/sys/checkUpdate")
                .header(Constant.APP_VERSION_KEY, "1.0.0")
                .header(Constant.APP_SYSTEM_TYPE_KEY, "android"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath(RESULT_CODE_PATH).value(AppCode.SUCCESS.getCode()));
    }

    @Test
    @Ignore
    public void sendVerifyCode() throws Exception {

        String mobile = RandomUtils.randomAllMobile();
        mockMvc.perform(post("/api/sys/sendVerifyCode")
                .param("mobile",mobile)
                .header(Constant.APP_VERSION_KEY, "1.0.0")
                .header(Constant.APP_SYSTEM_TYPE_KEY, "android"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath(RESULT_CODE_PATH).value(AppCode.SUCCESS.getCode()));

    }

}
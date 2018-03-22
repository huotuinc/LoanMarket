/*
 *  版权所有:杭州火图科技有限公司
 *  地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼在地图中查看
 *
 *  (c) Copyright Hangzhou Hot Technology Co., Ltd.
 *  Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 *  2013-2015. All rights reserved.
 */

package com.huotu.loanmarket.webapi.controller.base;


import com.huotu.loanmarket.common.Constant;
import com.huotu.loanmarket.common.utils.RandomUtils;
import com.huotu.loanmarket.service.config.ServiceConfig;
import com.huotu.loanmarket.service.entity.sesame.Industry;
import com.huotu.loanmarket.service.entity.user.User;
import com.huotu.loanmarket.service.enums.SesameEnum;
import com.huotu.loanmarket.service.repository.sesame.IndustryRepository;
import com.huotu.loanmarket.service.repository.user.UserRepository;
import com.huotu.loanmarket.webapi.config.MvcConfig;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.DigestUtils;
import org.springframework.web.context.WebApplicationContext;

import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.util.UUID;


/**
 * Created by cosy on 2016/4/22.
 */

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ServiceConfig.class, MvcConfig.class})
@ActiveProfiles(Constant.PROFILE_DEV)
public abstract class BaseTest {

    @Autowired
    private WebApplicationContext context;
    @Autowired
    protected UserRepository userRepository;
    @Autowired
    protected IndustryRepository industryRepository;
    Integer merchantId;


    protected MockMvc mockMvc;
    protected static final String RESULT_CODE_PATH = "$.resultCode";

    //初始化mockMvc
    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
        merchantId = 1;
    }

    protected User mockUser() throws UnsupportedEncodingException {
        String password = DigestUtils.md5DigestAsHex(RandomUtils.randomString().getBytes("UTF-8"));
        User user = new User();
        user.setUserToken(UUID.randomUUID().toString());
        user.setMerchantId(merchantId);
        user.setUserName(RandomUtils.randomAllMobile());
        user.setPassword(password);
        LocalDateTime localDateTime = LocalDateTime.now();
        user.setUserToken(RandomUtils.randomString());
        user.setRegTime(localDateTime);

        return userRepository.saveAndFlush(user);
    }

    protected Industry mockIndusTry(Long userId, String orderId) {
        Industry industry = new Industry();
        industry.setBiz_code(SesameEnum.IndustryType.E_COMMERCE_INDUSTRY);
        industry.setMerchantId(merchantId);
        industry.setOrderId(orderId);
        industry.setSettlement(SesameEnum.Settlement.EMPTY);
        industry.setStatement(UUID.randomUUID().toString());
        industry.setUserId(userId);
        industry.setStatus(SesameEnum.Status.STATUS_ONE);
        return industryRepository.saveAndFlush(industry);
    }
}





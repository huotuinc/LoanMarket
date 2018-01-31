/*
 *  版权所有:杭州火图科技有限公司
 *  地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼在地图中查看
 *
 *  (c) Copyright Hangzhou Hot Technology Co., Ltd.
 *  Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 *  2013-2015. All rights reserved.
 */

package com.huotu.loanmarket.webapi.base;


import com.huotu.loanmarket.common.Constant;
import com.huotu.loanmarket.service.config.ServiceConfig;
import com.huotu.loanmarket.webapi.config.MvcConfig;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.context.WebApplicationContext;

/**
 * Created by cosy on 2016/4/22.
 */

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ServiceConfig.class, MvcConfig.class})
@ActiveProfiles(Constant.PROFILE_UNIT_TEST)
public abstract class BaseTest {

    @Autowired
    private WebApplicationContext context;
}





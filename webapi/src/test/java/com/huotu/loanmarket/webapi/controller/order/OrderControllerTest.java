/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼在地图中查看
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2016-2018. All rights reserved.
 */

package com.huotu.loanmarket.webapi.controller.order;

import com.huotu.loanmarket.service.service.order.OrderService;
import com.huotu.loanmarket.webapi.controller.base.BaseTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author guomw
 * @date 2018/2/28
 */
public class OrderControllerTest extends BaseTest {

    @Autowired
    private OrderService orderService;

    @Test
    public  void orderRecycleByNotPayTest(){


        orderService.orderRecycleByNotPay();

    }

}

/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼在地图中查看
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2016-2018. All rights reserved.
 */

package com.huotu.loanmarket.webschedule.service;

import com.huotu.loanmarket.service.service.order.OrderService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * 订单监控相关
 * @author guomw
 * @date 2018/2/28
 */
@Service
public class OrderListenSchedule {
    private static final Log log = LogFactory.getLog(OrderListenSchedule.class);

    @Autowired
    private OrderService orderService;

    /**
     * 回收未支付的订单
     * 每5分钟执行一次
     */
    @Scheduled(fixedDelay = 5*60*1000)
    public void orderRecycleByNotPay(){
        log.info("开始执行未支付订单回收操作");
        long count= orderService.orderRecycleByNotPay();
        log.info("执行未支付订单回收操作完成,共"+count+"条");
    }
}

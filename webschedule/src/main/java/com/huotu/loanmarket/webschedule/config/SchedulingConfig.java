/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2017. All rights reserved.
 *
 */

package com.huotu.loanmarket.webschedule.config;

import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

import javax.annotation.PreDestroy;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;

/**
 * @author luyuanyuan on 2017/12/15.
 */
@Configuration
@EnableScheduling
class SchedulingConfig implements SchedulingConfigurer {

    private ExecutorService executorService = taskExecutor();

    @Override
    public void configureTasks(ScheduledTaskRegistrar scheduledTaskRegistrar) {
        scheduledTaskRegistrar.setScheduler(executorService);
    }

    @PreDestroy
    public void beforeClose() {
        executorService.shutdown();
    }

    private ExecutorService taskExecutor() {
        ScheduledExecutorService executorService = new ScheduledThreadPoolExecutor(20,
                new BasicThreadFactory.Builder().namingPattern("superloan-schedule-pool-%d").daemon(true).build());
        return executorService;
    }

}

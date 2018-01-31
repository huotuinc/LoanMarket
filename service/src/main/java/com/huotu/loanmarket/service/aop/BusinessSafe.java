/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼在地图中查看
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2016-2018. All rights reserved.
 */

package com.huotu.loanmarket.service.aop;

import java.lang.annotation.*;

/**
 * 声明为业务安全的方法，如果参数中有 {@link BusinessLocker}则使用其功能，否者默认使用第一个参数作为业务锁
 * Created by helloztt on 2017-01-09.
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface BusinessSafe {
}

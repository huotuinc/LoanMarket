/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼在地图中查看
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2017. All rights reserved.
 */

package com.huotu.loanmarket.service.model.order;

import com.huotu.loanmarket.common.Constant;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

/**
 * @author helloztt
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderSearchCondition {
    private String userName;
    private Long userId;
    private Integer orderType;
    private Integer authStatus;
    private Integer payStatus;

    private int pageIndex = 1;
    private int pageSize = Constant.BACKEND_DEFAULT_PAGE_SIZE;

    /**
     * 支付开始时间
     */
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate payTimeBegin;
    /**
     * 支付结束时间
     */
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate payTimeEnd;


    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate createTimeBegin;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate createTimeEnd;


    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate authTimeBegin;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate authTimeEnd;
}

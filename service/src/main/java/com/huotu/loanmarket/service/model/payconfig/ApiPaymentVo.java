/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼在地图中查看
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2016-2018. All rights reserved.
 */

package com.huotu.loanmarket.service.model.payconfig;

import lombok.Getter;
import lombok.Setter;

/**
 * 支付方式实体
 * @author wm
 */
@Getter
@Setter
public class ApiPaymentVo {
    /**
     * 支付方式类型id
     */
    private int payType;
    /**
     * 名称
     */
    private String name;
    /**
     * 备注信息
     */
    private String remark;
}
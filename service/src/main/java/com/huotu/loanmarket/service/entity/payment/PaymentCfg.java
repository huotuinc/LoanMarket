/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼在地图中查看
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2016-2018. All rights reserved.
 */

package com.huotu.loanmarket.service.entity.payment;

import com.huotu.loanmarket.common.Constant;
import com.huotu.loanmarket.service.enums.OrderEnum;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 *
 * @author hxh
 * @date 2017-12-12
 */
@Getter
@Setter
@Entity
@Table(name = "zx_payment_cfg")
public class PaymentCfg {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long paymentId;
    /**
     * 支付方式名称
     */
    @Column(name = "pay_name", length = 50)
    private String payName;
    /**
     * 支付类型
     */
    @Column(name = "pay_type",columnDefinition = "tinyint")
    private OrderEnum.PayType payType;
    /**
     * 详细备注
     */
    @Column(name = "remark")
    private String remark;
    /**
     * 排序编号（越大越往前）
     */
    @Column(name = "order_num")
    private int sortNum;
    /**
     * 启用状态，默认启用
     */
    @Column(name = "status")
    private boolean status = true;
    /**
     * 商户编号
     */
    @Column(name = "merchant_id")
    private Integer merchantId= Constant.MERCHANT_ID;

    /**
     * 创建时间
     */
    @Column(name = "createTime", columnDefinition = "timestamp")
    private LocalDateTime createTime=LocalDateTime.now();

}

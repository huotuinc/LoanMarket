/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼在地图中查看
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2016-2018. All rights reserved.
 */

package com.huotu.loanmarket.service.entity.order;

import com.huotu.loanmarket.common.Constant;
import com.huotu.loanmarket.service.enums.OrderEnum;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;


/**
 *
 * @author hot
 * @date 2017/11/21
 */
@Getter
@Setter
@Entity
@Table(name = "zx_order_log")
public class OrderLog {

    /** log_id */
    @Id
    @Column(name = "log_id", unique = true, nullable = false, length = 10)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long logId;
    /**
     * 商户id
     */
    @Column(name = "merchant_id")
    private Integer merchant= Constant.MERCHANT_ID;


    /** 借款单号 */
    @Column(name = "order_id",  length = 40)
    private String orderId;

    /** 操作人ID */
    @Column(name = "user_id")
    private Long userId;

    /** 操作人 */
    @Column(name = "op_name",  length = 20)
    private String opName;

    /** 描述 */
    @Column(name = "log_text", length = 500)
    private String logText;

    /** 操作时间 */
    @Column(name = "actTime",columnDefinition = "timestamp")
    private LocalDateTime actTime;

    /** 类型 */
    @Column(name = "log_type",  length = 3,columnDefinition = "tinyint")
    private OrderEnum.LogType logType;

    /** 是否成功 (暂时无用，全部填写成功--1)*/
    @Column(name = "result",  length = 3)
    private Integer result;


}

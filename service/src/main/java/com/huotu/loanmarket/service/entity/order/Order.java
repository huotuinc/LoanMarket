/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼在地图中查看
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2016-2018. All rights reserved.
 */

package com.huotu.loanmarket.service.entity.order;

import com.huotu.loanmarket.service.entity.user.User;
import com.huotu.loanmarket.service.enums.OrderEnum;
import com.huotu.loanmarket.service.enums.UserAuthorizedStatusEnums;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @author luyuanyuan on 2018/1/31.
 */
@Getter
@Setter
@Entity
@Table(name = "zx_orders")
public class Order {
    /**
     * 订单号
     */
    @Id
    @Column(name = "order_id", unique = true, nullable = false, length = 40)
    private String orderId;

    /**
     * 用户id
     */
    @JoinColumn(name = "user_id")
    @ManyToOne(cascade = {CascadeType.REFRESH}, fetch = FetchType.LAZY)
    private User userId;

    /**
     * 姓名
     */
    @Column(name = "real_name",length = 20)
    private String realName;

    /**
     * 手机
     */
    @Column(name = "mobile",length = 15)
    private String mobile;

    /**
     * 身份证
     */
    @Column(name = "id_card_no",length = 20)
    private String idCardNo;

    /**
     * 账户
     */
    @Column(name = "account_no",length = 50)
    private String accountNo;


    /**
     * 订单类型
     */
    @Column(name = "order_type")
    private OrderEnum.OrderType orderType=OrderEnum.OrderType.BACKLIST_BUS;

    /**
     * 支付类型
     */
    @Column(name = "pay_type")
    private OrderEnum.PayType payType= OrderEnum.PayType.OTHER;

    /**
     * 支付状态 0 未支付  1 已支付
     */
    @Column(name = "pay_status")
    private int payStatus=0;

    /**
     * 订单状态
     */
    @Column(name = "order_status")
    private OrderEnum.OrderStatus orderStatus=OrderEnum.OrderStatus.Normal;

    /**
     * 创建时间
     */
    @Column(name = "createTime", columnDefinition = "timestamp")
    private LocalDateTime createTime;

    /**
     * 认证状态
     * 1：未认证 2：认证失败 3: 已认证 4：过期
     */
    @Column(name = "auth_status", columnDefinition = "tinyint default 0")
    private UserAuthorizedStatusEnums authStatus = UserAuthorizedStatusEnums.AUTH_NOT;

    /**
     * 任务id
     */
    @Column(name = "task_id")
    private String taskId;

}

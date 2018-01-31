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
import com.huotu.loanmarket.service.enums.UserAuthorizedStatusEnums;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

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
     * 运营商认证状态
     * 1：未认证 2：认证失败 3: 已认证 4：过期
     */
    @Column(name = "flg_carrier", columnDefinition = "tinyint default 0")
    private UserAuthorizedStatusEnums flgCarrier = UserAuthorizedStatusEnums.AUTH_NOT;

    /**
     * 电商认证状态
     * 1：未认证 2：认证失败 3: 已认证 4：过期
     */
    @Column(name = "flg_ds", columnDefinition = "tinyint default 0")
    private UserAuthorizedStatusEnums flgDs = UserAuthorizedStatusEnums.AUTH_NOT;
}

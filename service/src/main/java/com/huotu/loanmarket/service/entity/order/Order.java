package com.huotu.loanmarket.service.entity.order;

import com.huotu.loanmarket.service.enums.UserAuthorizedStatusEnums;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

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
    @Column(name = "user_id")
    private Long userId;

    /**
     * 运营商认证状态
     * 1：未认证 2：认证失败 3: 已认证 4：过期
     */
    @Column(name = "flg_carrier", columnDefinition = "tinyint default 0")
//    @Convert(converter = EnumToIntegerConverter.UserAuthorizedStatusEnumsConverter.class)
    private Integer flgCarrier = UserAuthorizedStatusEnums.AUTH_NOT.getCode();

    /**
     * 电商认证状态
     * 1：未认证 2：认证失败 3: 已认证 4：过期
     */
    @Column(name = "flg_ds", columnDefinition = "tinyint default 0")
//    @Convert(converter = EnumToIntegerConverter.UserAuthorizedStatusEnumsConverter.class)
    private Integer flgDs = UserAuthorizedStatusEnums.AUTH_NOT.getCode();
}

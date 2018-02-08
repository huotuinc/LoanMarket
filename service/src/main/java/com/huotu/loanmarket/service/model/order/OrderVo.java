package com.huotu.loanmarket.service.model.order;

import com.huotu.loanmarket.service.enums.OrderEnum;
import com.huotu.loanmarket.service.enums.UserAuthorizedStatusEnums;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author luyuanyuan on 2018/2/7.
 */
@Getter
@Setter
public class OrderVo {

    /**
     * 订单号
     */
    private String orderId;

    /**
     * 姓名
     */
    private String realName;

    /**
     * 手机
     */
    private String mobile;


    /**
     * 账户
     */
    private String accountNo;

    /**
     * 订单类型
     */
    private OrderEnum.OrderType orderType = OrderEnum.OrderType.BACKLIST_BUS;

    /**
     * 支付状态 0 未支付  1 已支付
     */
    private OrderEnum.PayStatus payStatus = OrderEnum.PayStatus.NOT_PAY;

    /**
     * 订单金额
     */
    private BigDecimal payAmount =BigDecimal.ZERO;


    /**
     * 支付时间
     */
    private String payTime;


    /**
     * 创建时间
     */
    @Column(name = "createTime", columnDefinition = "timestamp")
    private LocalDateTime createTime=LocalDateTime.now();

    /**
     * 认证状态
     * 0：未认证 1：认证失败 2: 已认证 4：过期 5:认证中
     */
    private UserAuthorizedStatusEnums authStatus = UserAuthorizedStatusEnums.AUTH_NOT;

    /**
     * 电商账户名
     */
    private String accountName;

}

package com.huotu.loanmarket.service.model.system;

import com.huotu.loanmarket.service.entity.system.DataStatisticsByDay;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.aspectj.weaver.patterns.ThisOrTargetAnnotationPointcut;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author helloztt
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DataStatisticsVo {
    private Integer merchantId;
    /**
     * 新增现金收入
     */
    private BigDecimal orderAmount;
    private String orderAmountUrl;
    /**
     * 总现金收入
     */
    private BigDecimal totalOrderAmount;
    /**
     * 新增用户数量
     */
    private int userCount;
    /**
     * 总用户数量
     */
    private int totalUserCount;
    /**
     * 新增订单数量
     */
    private int orderCount;
    /**
     * 支付成功订单数量
     */
    private int paySuccessOrderCount;
    /**
     * 待支付订单数量
     */
    private int payingOrderCount;
    /**
     * 认证成功数量
     */
    private int authSuccessCount;
    /**
     * 认证失败数量
     */
    private int authFailureCount;

    private LocalDateTime beginTime;
    private LocalDateTime lastTime;

    public DataStatisticsVo(DataStatisticsByDay dayData, Long totalUserCount, BigDecimal totalOrderAmount) {
        this.merchantId = dayData.getMerchantId();
        this.orderAmount = dayData.getOrderAmount();
        this.userCount = dayData.getUserCount();
        this.orderCount = dayData.getOrderCount();
        this.paySuccessOrderCount = dayData.getOrderPayCount();
        this.payingOrderCount = this.orderCount - this.paySuccessOrderCount;
        this.authSuccessCount = dayData.getAuthSuccessCount();
        this.authFailureCount = dayData.getAuthFailureCount();
        this.lastTime = dayData.getLastHourTime();
        this.beginTime = lastTime.toLocalDate().atStartOfDay();
        this.totalUserCount = totalUserCount.intValue();
        this.totalOrderAmount = totalOrderAmount;
    }
}

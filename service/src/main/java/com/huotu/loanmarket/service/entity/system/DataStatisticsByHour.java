package com.huotu.loanmarket.service.entity.system;

import com.huotu.loanmarket.common.Constant;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * 每小时数据统计
 *
 * @author helloztt
 */
@Getter
@Setter
@Entity
@Table(name = "zx_statistics_hour")
public class DataStatisticsByHour {
    @Id
    @Column(name = "data_id", unique = true, nullable = false, length = 10)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer dataId;
    /**
     * 商户id
     */
    @Column(name = "merchant_id")
    private Integer merchantId = Constant.MERCHANT_ID;
    /**
     * 订单成功金额
     */
    @Column(name = "order_amount")
    private BigDecimal orderAmount = BigDecimal.ZERO;
    /**
     * 用户人数
     */
    @Column(name = "user_count")
    private int userCount;
    /**
     * 订单数量
     */
    @Column(name = "order_count")
    private int orderCount;
    /**
     * 认证成功数量
     */
    @Column(name = "auth_success_count")
    private int authSuccessCount;
    /**
     * 认证失败数量
     */
    @Column(name = "auth_failure_count")
    private int authFalureCount;

}

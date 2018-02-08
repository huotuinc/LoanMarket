package com.huotu.loanmarket.service.entity.system;

import com.huotu.loanmarket.common.Constant;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 每天数据统计
 *
 * @author helloztt
 */
@Getter
@Setter
@Entity
@Table(name = "zx_statistics_day")
@NoArgsConstructor
public class DataStatisticsByDay {
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
     * 订单支付数量
     */
    @Column(name = "order_pay_count")
    private int orderPayCount;
    /**
     * 认证成功数量
     */
    @Column(name = "auth_success_count")
    private int authSuccessCount;
    /**
     * 认证失败数量
     */
    @Column(name = "auth_failure_count")
    private int authFailureCount;
    /**
     * 统计时间(创建时间）
     */
    @Column(name = "statistics_time", columnDefinition = "timestamp")
    private LocalDateTime createTime = LocalDateTime.now();
    /**
     * 统计结束时间(包含）
     */
    @Column(name = "statistics_end_time", columnDefinition = "datetime")
    private LocalDate endTime = createTime.toLocalDate();
    /**
     * 统计起始时间（不包含）
     */
    @Column(name = "statistics_begin_time", columnDefinition = "datetime")
    private LocalDate beginTime = endTime.minusDays(1);
    /**
     * 统计截止时间
     */
    @Transient
    private LocalDateTime lastHourTime;

    public DataStatisticsByDay(BigDecimal orderAmount, Long userCount, Long orderCount, Long orderPayCount, Long authSuccessCount, Long authFailureCount,LocalDateTime lastHourTime) {
        this.orderAmount = orderAmount;
        this.userCount = userCount.intValue();
        this.orderCount = orderCount.intValue();
        this.orderPayCount = orderPayCount.intValue();
        this.authSuccessCount = authSuccessCount.intValue();
        this.authFailureCount = authFailureCount.intValue();
        this.lastHourTime = lastHourTime;
    }
}

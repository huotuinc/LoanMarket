package com.huotu.loanmarket.service.entity.ds;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * 电商用户账户信息
 * @author luyuanyuan on 2018/2/1.
 */
@Getter
@Setter
@Entity
@Table(name = "zx_account_info")
public class AccountInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /** order_id */
    @Column(name = "order_id")
    private String orderId;

    /**
     * 账户余额
     */
    @Column(name = "account_balance")
    private BigDecimal accountBalance;

    /**
     * 金融帐户余额
     */
    @Column(name = "financial_account_balance")
    private BigDecimal financialAccountBalance;

    /**
     * 信用分数。可能有小数
     */
    @Column(name = "credit_point")
    private String creditPoint;

    /**
     * 信用额度
     */
    @Column(name = "credit_quota")
    private BigDecimal creditQuota;

    /**
     * 消费额度（白条欠款）
     */
    @Column(name = "consume_quota")
    private BigDecimal consumeQuota;
}

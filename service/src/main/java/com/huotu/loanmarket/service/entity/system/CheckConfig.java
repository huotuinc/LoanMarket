package com.huotu.loanmarket.service.entity.system;


import com.huotu.loanmarket.common.Constant;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 检测配置
 */
@Entity
@Getter
@Setter
@Table(name = "zx_check_config")
public class CheckConfig {

    /**
     * 商户id
     */
    @Id
    @Column(name = "merchant_id")
    private Integer merchantId = Constant.MERCHANT_ID;

    /**
     * 黑名单检测
     */
    private Long blackListCheck;
    /**
     * 运营商风险检测
     */
    private Long operatorCheck;
    /**
     * 电商风险检测
     */
    private Long electronicBusinessCheck;
}

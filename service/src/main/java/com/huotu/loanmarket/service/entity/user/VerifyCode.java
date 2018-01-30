/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼在地图中查看
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2016-2017. All rights reserved.
 */

package com.huotu.loanmarket.service.entity.user;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import java.time.LocalDateTime;

/**
 * 验证码
 * @author guomw
 * @date 13/11/2017
 */
@Getter
@Setter
@Entity
@Table(name = "zx_sms_verifycode")
public class VerifyCode {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    /**
     * 商户id
     */
    @Column(name = "merchant_id")
    private Integer merchantId;

    /**
     * 验证码
     */
    @Column(name = "verify_code",length = 10)
    private String verifyCode;
    /**
     * 手机号
     */
    @Column(name = "mobile",length = 15)
    private String mobile;
    /**
     * 添加时间
     */
    @Column(name = "createtime",columnDefinition = "timestamp")
    private LocalDateTime createTime;

    /**
     * 使用状态
     * 1:已使用
     * 0:未使用
     */
    @Column(name = "use_status")
    private boolean useStatus;



}

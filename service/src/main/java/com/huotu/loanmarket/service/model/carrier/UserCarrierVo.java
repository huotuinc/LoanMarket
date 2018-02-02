package com.huotu.loanmarket.service.model.carrier;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * @author luyuanyuan on 2018/2/2.
 */
@Getter
@Setter
public class UserCarrierVo {

    /**
     * 运营商认证姓名
     */
    private String realName;

    /**
     * 身份证号
     */
    private String identityCode;

    /**
     * 入网时间
     */
    private String netTime;
    /**
     * 手机号
     */
    private String mobile;

    /**
     * 号码归属地
     */
    private String mobileAddr;

    /**
     * 状态
     */
    private String status;

    /**
     * 账户余额
     */
    private BigDecimal accountBalance;

    /**
     * 近6个月互通号码数量
     */
    private String mutualNumber;

    /**
     * 前10联系人黑名单人数占比
     */
    private Double blackRadio;

    /**
     * 前10联系人信贷逾期名单人数占比
     */
    private Double blackCreditRadio;

    /**
     * 前10联系人近3月平均申请平台数
     */
    private String applyCount;

    /**
     * 前10联系人近3月申请2个及以上平台的人数
     */
    private String applyCountOverTwo;

    /**
     * 近3月通话活跃天数
     */
    private int activeDayCallThree;

    /**
     * 近3月无通话静默天数
     */
    private int silenceDayCallThree;

    /**
     * 近3月连续无通话静默>=3天的次数
     */
    private int continueSilenceDayOverThree;

}

package com.huotu.loanmarket.service.model.ds;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * @author luyuanyuan on 2018/2/2.
 */
@Getter
@Setter
public class DsVo {

    /**
     * 用户名
     */
    private String userName;
    /**
     * 用户级别
     */
    private String userLevel;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 真实姓名
     */
    private String name;
    /**
     * 邮箱。如：123@qq.com
     */
    private String email;

    /**
     * 实名认证姓名。脱敏部分用＊
     */
    private String realName;

    /**
     * 性别
     */
    private String gender;

    /**
     * 绑定手机号。脱敏部分用＊
     */
    private String mobile;

    /**
     * 实名认证身份证。脱敏部分用＊
     */
    private String identityCode;

    /**
     * 账户余额
     */
    private BigDecimal accountBalance;

    /**
     * 金融帐户余额
     */
    private BigDecimal financialAccountBalance;

    /**
     * 信用分数。可能有小数
     */
    private String creditPoint;

    /**
     * 信用额度
     */
    private BigDecimal creditQuota;

    /**
     * 消费额度（白条欠款）
     */
    private BigDecimal consumeQuota;
}

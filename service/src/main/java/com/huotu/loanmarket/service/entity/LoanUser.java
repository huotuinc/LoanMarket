package com.huotu.loanmarket.service.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @author hxh
 * @date 2017-10-23
 */
@Getter
@Setter
@Entity
@Cacheable(false)
@Table(name = "sl_users")
public class LoanUser {
    /**
     * 用户编号
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;
    /**
     * 用户名
     */
    @Column(name = "uname", length = 20)
    private String userName;
    /**
     * 密码
     */
    @Column(name = "password", length = 32)
    private String password;

    /**
     * 真实姓名
     */
    @Column(name = "realname", length = 50)
    private String realName;

    /**
     * 设备号
     */
    @Column(name = "hwid", length = 50)
    private String equipmentId;

    /**
     * 设备类型
     */
    @Column(name = "mobile_type")
    private String mobileType;

    /**
     * os类型
     */
    @Column(name = "os_type")
    private String osType;
    /**
     * os版本
     */
    @Column(name = "os_version", length = 20)
    private String osVersion;
    /**
     * 渠道编号
     */
    @Column(name = "ttid", length = 30)
    private String channelId;
    /**
     * 删除标志位
     */
    @Column(name = "disabled")
    private boolean disabled;
    /**
     * 是否冻结
     */
    @Column(name = "locked")
    private boolean locked;
    /**
     * 注册ip
     */
    @Column(name = "reg_ip", length = 16)
    private String regIp;
    /**
     * 最后登录ip
     */
    @Column(name = "last_loginip", length = 16)
    private String lastLoginIp;
    /**
     * 登录次数
     */
    @Column(name = "login_count")
    private int loginCount;
    /**
     * 备注
     */
    @Column(name = "remark", length = 400)
    private String remark;
    /**
     * 备注类型
     */
    @Column(name = "remark_type")
    private int remarkType;
    /**
     * 用户token
     */
    @Column(name = "user_token", length = 64)
    private String userToken;
    /**
     * 身份证号码
     */
    @Column(name = "user_card_no", length = 19)
    private String userCardNo;


}

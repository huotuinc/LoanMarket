/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼在地图中查看
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2016-2018. All rights reserved.
 */

package com.huotu.loanmarket.service.entity.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.huotu.loanmarket.service.enums.UserAuthorizedStatusEnums;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * 邀请表，记录用户被邀请的日志
 *
 * @author hot
 * @date 2018/1/18
 */
@Entity
@Getter
@Setter
@Table(name = "zx_user_invite", uniqueConstraints = {@UniqueConstraint(columnNames = "userId")})
public class Invite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "invite_id")
    private Long id;

    @Column(name = "merchant_id")
    private Integer merchantId;

    /**
     * 用户
     */
    @Column(name = "user_id")
    private Long userId;

    /**
     * 用户名
     */
    @Column(name = "user_name", length = 20)
    private String userName;

    /**
     * 真实姓名
     */
    @Column(name = "real_name", length = 50)
    private String realName;

    /**
     * 邀请者(即上线)
     */
    @Column(name = "inviter_id")
    private Long inviterId;

    /**
     * 邀请者
      */
    @Column(name = "inviter_name", length = 20)
    private String inviterName;

    /**
     * 邀请时间
     */
    @Column(name = "invite_time", columnDefinition = "timestamp")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime time;


    /**
     * 认证状态
     */
    @Column(name = "auth_status", columnDefinition = "tinyint")
    private UserAuthorizedStatusEnums authStatus = UserAuthorizedStatusEnums.AUTH_NOT;


    /**
     * 认证已奖励
     */
    @Column(name = "auth_rewarded")
    private Boolean authRewarded = false;

    /**
     * 认证奖励时间
     */
    @Column(name = "auth_rewarded_time")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime authRewardedTime;


}

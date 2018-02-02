/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼在地图中查看
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2016-2017. All rights reserved.
 */

package com.huotu.loanmarket.service.entity.user;


import com.huotu.loanmarket.common.Constant;
import com.huotu.loanmarket.service.enums.UserAuthorizedStatusEnums;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;


/**
 * @author guomw
 */
@Getter
@Setter
@Entity
@Table(name = "zx_users")
public class User {
    /**
     * 用户编号
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    /**
     * 商户id
     */
    @Column(name = "merchant_id")
    private Integer merchantId= Constant.MERCHANT_ID;

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
     * 头像
     */
    @Column(name = "headimg", length = 200)
    private String headimg;

    /**
     * 真实姓名
     */
    @Column(name = "real_name", length = 50)
    private String realName;
    /**
     * QQ
     */
    @Column(name = "qq", length = 20)
    private String qq;

    /**
     * 城市名称 APP获取的地址
     */
    @Column(name = "city_name", length = 30)
    private String cityName;

    /**
     * 城市代码 APP获取的地址
     */
    @Column(name = "city_code")
    private int cityCode;

    /**
     * 面部照片
     */
    @Column(name = "face_photo", length = 250)
    private String facePhoto;

    /**
     * 设备号
     */
    @Column(name = "hwid", length = 50)
    private String equipmentId;
    /**
     * app版本
     */
    @Column(name = "app_version", length = 20)
    private String appVersion;
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
    @Column(name = "channel_id", length = 30)
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
     * 注册时间
     */
    @Column(name = "reg_time", columnDefinition = "datetime")
    private LocalDateTime regTime;
    /**
     * 最后登录时间
     */
    @Column(name = "last_logintime", columnDefinition = "datetime")
    private LocalDateTime lastLoginTime;
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
     * 邀请者(即上线)
     */
    @Column(name = "inviter_id")
    private Long inviterId;



    /**
     * 认证状态
     */
    @Column(name = "auth_status", columnDefinition = "tinyint")
    private UserAuthorizedStatusEnums authStatus = UserAuthorizedStatusEnums.AUTH_NOT;


    /**
     * 用户是否是可用状态：
     * 1.未删除
     * 2.未冻结
     *
     * @return
     */
    public boolean usable() {
        return !disabled && !locked;
    }


}

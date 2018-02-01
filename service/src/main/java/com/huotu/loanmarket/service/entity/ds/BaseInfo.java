package com.huotu.loanmarket.service.entity.ds;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 电商用户基本信息
 * @author luyuanyuan on 2018/2/1.
 */
@Getter
@Setter
@Entity
@Table(name = "zx_base_info")
public class BaseInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /** order_id */
    @Column(name = "order_id")
    private String orderId;

    /**
     * 用户名。如：jingdong
     */
    @Column(name = "user_name")
    private String userName;

    /**
     * 邮箱。如：123@qq.com
     */
    @Column(name = "email")
    private String email;

    /**
     * 用户级别
     */
    @Column(name = "user_level")
    private String userLevel;

    /**
     * 昵称
     */
    @Column(name = "nick_name")
    private String nickName;

    /**
     * 真实姓名
     */
    @Column(name = "name")
    private String name;

    /**
     * 实名认证姓名。脱敏部分用＊
     */
    @Column(name = "real_name")
    private String realName;

    /**
     * 性别
     */
    @Column(name = "gender")
    private String gender;

    /**
     * 绑定手机号。脱敏部分用＊
     */
    @Column(name = "mobile")
    private String mobile;

    /**
     * 实名认证身份证。脱敏部分用＊
     */
    @Column(name = "identity_code")
    private String identityCode;
}

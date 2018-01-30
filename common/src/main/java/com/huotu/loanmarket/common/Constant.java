/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼在地图中查看
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2016-2018. All rights reserved.
 */

package com.huotu.loanmarket.common;

import java.math.BigDecimal;

/**
 * 常量定义
 *
 * @author guomw
 * @date 14/11/2017
 */
public class Constant {


    public static final String PROFILE_DEV = "development";
    public static final String PROFILE_API = "api";
    public static final String PROFILE_DEBUG = "debug";
    //测试环境
    public static final String PROFILE_TEST = "test";
    //单元测试
    public static final String PROFILE_UNIT_TEST = "unit_test";
    public static final String PROFILE_UN_CHECK = "uncheck";

    /**
     * %
     */
    public static final BigDecimal RATE = BigDecimal.valueOf(100L);
    /**
     * 小数位数
     */
    public static final int SCALE = 2;

    public static final int PRECISION = 9;
    /**
     * 银行家舍入发
     */
    public static final int ROUNDING_MODE = BigDecimal.ROUND_HALF_EVEN;

    /**
     * 默认页面大小
     */
    public static final int PAGE_SIZE = 20;
    /**
     * 默认页面大小，用于 RequestParam 设置 defaultValue
     */
    public static final String PAGE_SIZE_STR = "10";


    /**
     * 签名密钥
     */
    public static final String SECRET_KEY = "4165a8d240b29af3f41818d10599d0d1";

    public static final String SIGN_KEY = "sign";


    /**
     * 密码长度
     */
    public static final int PASS_WORD_LENGTH = 32;

    /**
     * 验证码长度
     */
    public static final int VERIFY_CODE_LENGTH = 4;


    /**
     * 验证码有效期时间 10分钟
     */
    public static final int VERIFY_CODE_TIME_LENGTH = 10;

    /**
     * 时间戳的有效期时间,默认5分钟，单位秒
     */
    public static final int TIMESTAMP_VALID_TIME_LENGTH = 5 * 60;

    /**
     * 沙盒环境下不进行签名验证
     */
    public static boolean SANDBOX = true;
    /**
     *
     */
    public static String ENCODING_UTF8 = "utf-8";
    public static String COMMA = ",";

    /**
     * API Header 参数常量定义
     */
    public static final String APP_VERSION_KEY = "appVersion";
    public static final String APP_EQUIPMENT_NUMBER_KEY = "hwid";
    public static final String APP_MOBILE_TYPE_KEY = "mobileType";
    public static final String APP_SYSTEM_TYPE_KEY = "osType";
    public static final String APP_OS_VERSION_KEY = "osVersion";
    public static final String APP_USER_TOKEN_KEY = "userToken";
    public static final String APP_USER_ID_KEY = "userId";
    public static final String APP_MERCHANT_ID_KEY = "merchantId";
    public static final String APP_TIMESTAMP_KEY = "timestamp";
    public static final String APP_CITY_NAME_KEY = "cityName";
    public static final String APP_CITY_CODE_KEY = "cityCode";
    public static final String APP_CHANNELID_KEY = "channelId";

    /**
     *  商户ID
     */
    public static final Integer MERCHANT_ID =1;

    /**
     * 默认代理
     */
    public static final String DefaultAgent = "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/57.0.2987.133 Safari/537.36";


    /***
     * 接口消息内容常量定义
     */

    public static final Integer BACKEND_DEFAULT_PAGE_SIZE = 20;
    /**
     * 图片格式
     */
    public static final String[] exts = {"png", "jpg", "jpeg", "bmp", "PNG", "JPG", "JPEG", "BMP"};

    /**
     * 渠道管理后台登录session_key
     */
    public static final String CHANNEL_ADMIN_SESSION_KEY = "__channelKey";
    /**
     * 渠道管理后台登录session_id
     */
    public static final String CHANNEL_ADMIN_SESSION_ID = "__channelId";
    /**
     * 渠道管理后台登录session_Login_Name
     */
    public static final String CHANNEL_ADMIN_SESSION_LOGIN_NAME = "__channelLoginName";
}

/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼在地图中查看
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2016-2018. All rights reserved.
 */

package com.huotu.loanmarket.service.enums;

import com.huotu.loanmarket.common.enums.ICommonEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 用户相关的code
 *
 * @author guomw
 * @date
 */
@Getter
@AllArgsConstructor
public enum UserResultCode implements ICommonEnum {

    /**
     * 手机号码输入有误
     */
    CODE1(4101, "手机号码输入有误"),
    CODE2(4102, "用户密码输入有误"),
    CODE3(4103, "手机号码已被注册，请更换手机号码"),
    CODE4(4104, "手机号码已注册"),
    CODE5(4105, "用户不存在"),
    CODE6(4106, "密码错误"),
    CODE7(4107, "账号已冻结"),
    CODE8(4108, "验证码发送失败"),
    CODE9(4109, "短信验证码输入有误"),
    //改为2000
    CODE10(2000, "已发送成功，请注意查收！"),
    CODE11(4111, "短信验证码登录暂未开通"),
    CODE12(4112, "QQ号输入有误"),
    NO_BANKCARD(4113, "未找到银行卡信息"),
    ERROR_FORMAT_BANKCARD(4114, "银行卡号格式错误"),
    NOT_AUTH_IDCARD(4115, "请先完成身份认证"),
    USER_CONTACT_BOOK_INVALIDATE(4116, "通讯录数据错误"),
    USER_CONTACT_BOOK_EMPTY(4117, "通讯录为空"),
    REPORT_TONGDUN_EMPTY(4118, "风控信息不存在"),
    REPORT_TONGDUN_BUILDING(4119, "风控信息提交生成中"),
    REPORT_TONGDUN_BUILD_ERROR(4120, "风控信息生成错误"),
    REPORT_CARRIER_EMPTY(4121, "运营商信息不存在"),
    USER_LOCKED(4122, "用户不可用"),
    USER_NOT_AUTH(4123, "用户未完成认证"),
    NOT_SUFFICIENT_FUNDS(4124, "用户余额不足"),
    SMS_SCENETYPE_NOT_EXIST(4125, "短信模板不存在"),

    USER_IDCARD_BIRTH_ERROR(4126, "出生日期错误"),
    USER_IDCARD_PHOTO_EMPTY(4127, "身份证照未传"),
    USER_IDCARD_PHOTO_SAVE_ERROR(4128, "身份证照保存失败"),
    USER_IDCARD_NO_AUTH(4129, "身份证信息未认证"),
    USER_ORDER_NO_EXIT(4130, "借条数据不存在"),

    USER_CONTACT_EMPTY(4131, "联系人为空"),
    NOT_AUTH_CONTACT(4132, "请先完成联系人认证"),
    NOT_AUTH_BASICINFO(4133, "请先完成基本信息认证"),
    CONTANT_MOBILE_SAME(4134, "紧急联系人号码相同"),
    NOT_MOBILE_NUM(4135, "不是手机号"),

    USER_IMAGE_NOT_UPLOAD(4144, "图片未上传"),
    USER_IMAGE_UPLOAD_NOT_ALL(4145, "图片未上传完全"),;

    private int code;

    private String name;

}

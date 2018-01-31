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
 *
 * @author guomw
 * @date 2017/12/15
 */
@Getter
@AllArgsConstructor
public enum SmsTemplateSceneTypeEnum implements ICommonEnum {

    /**
     *verifyCode
     */
    SMS_VERIFY_CODE(0,"短信验证码","您的验证码是{code}，有效期{time}分钟。"),
    NOT_AUDIT(1,"待审核借条","您有一笔新的待审核款项，请立即处理！"),
    REPAYMENT_REMIND(2,"还款提醒,未逾期","您有一笔款项明天到期，请注意。"),
    REPAYMENT_EXPIRE_REMIND_LENDER(3,"还款提醒,已逾期,出借方","您有一笔款项已逾期，请注意！"),
    REPAYMENT_EXPIRE_REMIND_LOANER(4,"还款提醒,已逾期,借款方","您有一笔款项已逾期，请注意！"),
    NEW_REGISTER(5,"新用户注册密码提醒","恭喜您成功领取，请登录公众号查看，登录密码为手机号后6位！"),
    NEW_REGISTER_REWARD_NOTICE(6,"新用户注册奖励提醒","恭喜您成功注册过海有信！您已获得50元奖励，请下载过海有信APP后前往个人中心完成身份认证领取。"),

    ;
    private int code;
    private String name;
    private String template;

}

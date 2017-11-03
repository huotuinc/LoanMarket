package com.huotu.loanmarket.service.service;

import com.huotu.loanmarket.service.base.CrudService;
import com.huotu.loanmarket.service.entity.LoanVerifyCode;

/**
 * @author allan
 * @date 01/11/2017
 */
public interface VerifyCodeService extends CrudService<LoanVerifyCode, Long> {
    /**
     * 发送验证码
     *
     * @param mobile  接受者手机号
     * @param message 信息内容
     * @return
     */
    boolean sendCode(String mobile, String message);

    /**
     * 发送信息
     *
     * @param mobile  接受者手机号
     * @param message 信息内容
     * @return
     */
    boolean send(String mobile, String message) throws Exception;

    /**
     * 验证码验证
     *
     * @param mobile 接受者手机号
     * @param code   验证码
     * @return
     */
    boolean verifyCode(String mobile, String code);

    boolean codeCheck(String mobile, String code);
}

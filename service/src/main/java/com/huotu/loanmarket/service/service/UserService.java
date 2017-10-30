package com.huotu.loanmarket.service.service;

import com.huotu.loanmarket.service.base.CrudService;
import com.huotu.loanmarket.service.entity.LoanUser;

/**
 * @author hxh
 * @date 2017-10-27
 */
public interface UserService extends CrudService<LoanUser, Integer> {
    /**
     * 登录验证
     *
     * @param mobile
     * @param verifyCode
     * @return
     */
    LoanUser checkLogin(String mobile, String verifyCode);
}

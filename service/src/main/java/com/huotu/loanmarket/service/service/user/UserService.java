package com.huotu.loanmarket.service.service.user;

import com.huotu.loanmarket.service.aop.BusinessSafe;
import com.huotu.loanmarket.service.entity.user.User;
import com.huotu.loanmarket.service.exceptions.ErrorMessageException;
import org.springframework.transaction.annotation.Transactional;


/**
 * @Author hxh
 * @Date 2018/1/30 15:57
 */
public interface UserService {
    /**
     * 查询
     *
     * @param merchantId
     * @param userId
     * @return
     */
    User findByMerchantIdAndUserId(Integer merchantId, Long userId);


    /**
     * 用户注册
     *
     * @param user
     * @param verifyCode
     * @return
     * @throws ErrorMessageException
     */
    @BusinessSafe
    @Transactional(rollbackFor = Exception.class)
    User register(User user, String verifyCode) throws ErrorMessageException;
}

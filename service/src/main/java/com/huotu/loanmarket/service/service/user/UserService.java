package com.huotu.loanmarket.service.service.user;

import com.huotu.loanmarket.service.entity.user.User;


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
}

package com.huotu.loanmarket.service.service.user.impl;

import com.huotu.loanmarket.service.entity.user.User;
import com.huotu.loanmarket.service.repository.user.UserRepository;
import com.huotu.loanmarket.service.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Author hxh
 * @Date 2018/1/30 15:58
 */
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public User findByMerchantIdAndUserId(Integer merchantId, Long userId) {
        return userRepository.findByMerchantIdAndUserId(merchantId, userId);
    }
}

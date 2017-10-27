package com.huotu.loanmarket.service.service.impl;

import com.huotu.loanmarket.service.base.AbstractCrudService;
import com.huotu.loanmarket.service.entity.LoanUser;
import com.huotu.loanmarket.service.repository.LoanUserRepository;
import com.huotu.loanmarket.service.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author hxh
 * @date 2017-10-27
 */
@Service
public class UserServiceImpl extends AbstractCrudService<LoanUser, Integer> implements UserService {

    private final LoanUserRepository loanUserRepository;

    @Autowired
    public UserServiceImpl(LoanUserRepository repository) {
        super(repository);
        loanUserRepository = repository;
    }

    @Override
    public LoanUser checkLogin(String mobile, String verifyCode) {
        // TODO: 2017-10-26 验证码待测
        LoanUser user = loanUserRepository.findByAccount(mobile);
        if (user == null) {
            user = new LoanUser();
            user.setAccount(mobile);
            this.repository.save(user);
        }
        return user;
    }
}

package com.huotu.loanmarket.service.repository;

import com.huotu.loanmarket.service.base.JpaCrudRepository;
import com.huotu.loanmarket.service.entity.LoanUser;

/**
 *
 * @author hxh
 * @date 2017-10-24
 */
public interface LoanUserRepository extends JpaCrudRepository<LoanUser,Integer> {
    /**
     * 根据手机号查询用户
     * @param account
     * @return
     */
    LoanUser findByAccount(String account);
}

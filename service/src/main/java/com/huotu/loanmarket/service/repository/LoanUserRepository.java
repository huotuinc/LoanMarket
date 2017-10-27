package com.huotu.loanmarket.service.repository;

import com.huotu.loanmarket.service.entity.LoanUser;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by hxh on 2017-10-24.
 */
public interface LoanUserRepository extends JpaRepository<LoanUser,Integer>{
    /**
     * 根据手机号查询用户
     * @param account
     * @return
     */
    LoanUser findByAccount(String account);
}

package com.huotu.loanmarket.service.repository;

import com.huotu.loanmarket.service.base.JpaCrudRepository;
import com.huotu.loanmarket.service.entity.LoanVerifyCode;
import org.springframework.stereotype.Repository;

/**
 * @author allan
 * @date 01/11/2017
 */
@Repository
public interface LoanVerifyCodeRepository extends JpaCrudRepository<LoanVerifyCode, Long> {
    LoanVerifyCode findByMobile(String mobile);
}

package com.huotu.loanmarket.service.repository;

import com.huotu.loanmarket.service.base.JpaCrudRepository;
import com.huotu.loanmarket.service.entity.LoanUserApplyLog;
import org.springframework.stereotype.Repository;

/**
 *
 * @author hxh
 * @date 2017-10-26
 */
@Repository
public interface LoanUserApplyLogRepository extends JpaCrudRepository<LoanUserApplyLog, Integer> {
}

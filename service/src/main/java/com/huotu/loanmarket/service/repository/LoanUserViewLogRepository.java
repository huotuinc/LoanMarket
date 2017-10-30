package com.huotu.loanmarket.service.repository;

import com.huotu.loanmarket.service.base.JpaCrudRepository;
import com.huotu.loanmarket.service.entity.LoanUserViewLog;
import org.springframework.stereotype.Repository;

/**
 * @author hxh
 * @date 2017-10-26
 */
@Repository
public interface LoanUserViewLogRepository extends JpaCrudRepository<LoanUserViewLog, Integer> {
}

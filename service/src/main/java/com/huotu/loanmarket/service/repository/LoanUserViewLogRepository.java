package com.huotu.loanmarket.service.repository;

import com.huotu.loanmarket.service.base.JpaCrudRepository;
import com.huotu.loanmarket.service.entity.LoanUserViewLog;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author hxh
 * @date 2017-10-26
 */
@Repository
public interface LoanUserViewLogRepository extends JpaCrudRepository<LoanUserViewLog, Integer> {
    /**
     * 查询
     *
     * @param projectId
     * @return
     */
    List<LoanUserViewLog> findByProjectId(int projectId);
}

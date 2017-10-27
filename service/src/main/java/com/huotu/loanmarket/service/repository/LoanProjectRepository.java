package com.huotu.loanmarket.service.repository;

import com.huotu.loanmarket.service.entity.LoanProject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 *
 * @author hxh
 * @date 2017-10-24
 */
public interface LoanProjectRepository extends JpaRepository<LoanProject,Integer>,JpaSpecificationExecutor{
}

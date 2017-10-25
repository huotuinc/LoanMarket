package com.huotu.loanmarket.service.repositoryhxh;

import com.huotu.loanmarket.service.entity.LoanProject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Created by hxh on 2017-10-24.
 */
public interface LoanProjectRepository extends JpaRepository<LoanProject,Integer>,JpaSpecificationExecutor{
}

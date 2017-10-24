package com.huotu.loanmarket.service.Repository;

import com.huotu.loanmarket.service.entity.LoanUser;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by hxh on 2017-10-24.
 */
public interface LoanUserRepository extends JpaRepository<LoanUser,Integer>{
}

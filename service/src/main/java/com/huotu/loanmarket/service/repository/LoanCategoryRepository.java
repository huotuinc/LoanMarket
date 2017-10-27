package com.huotu.loanmarket.service.repository;

import com.huotu.loanmarket.service.base.JpaCrudRepository;
import com.huotu.loanmarket.service.entity.LoanCategory;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by hxh on 2017-10-24.
 */
public interface LoanCategoryRepository extends JpaCrudRepository<LoanCategory,Integer> {
}

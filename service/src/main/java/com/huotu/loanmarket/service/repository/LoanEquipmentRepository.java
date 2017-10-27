package com.huotu.loanmarket.service.repository;

import com.huotu.loanmarket.service.base.JpaCrudRepository;
import com.huotu.loanmarket.service.entity.LoanEquipment;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 *
 * @author hxh
 * @date 2017-10-26
 */
public interface LoanEquipmentRepository extends JpaCrudRepository<LoanEquipment,Integer>,JpaSpecificationExecutor {
}

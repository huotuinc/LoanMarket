package com.huotu.loanmarket.service.repository.system;

import com.huotu.loanmarket.service.entity.system.CheckConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface CheckConfigRepository extends JpaRepository<CheckConfig,Integer>,JpaSpecificationExecutor<CheckConfig> {

}

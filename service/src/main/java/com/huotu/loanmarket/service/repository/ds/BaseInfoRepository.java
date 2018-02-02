package com.huotu.loanmarket.service.repository.ds;

import com.huotu.loanmarket.service.entity.ds.BaseInfo;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author luyuanyuan on 2018/2/1.
 */
public interface BaseInfoRepository extends JpaRepository<BaseInfo, Long> {
    BaseInfo findByOrderId(String orderId);
}

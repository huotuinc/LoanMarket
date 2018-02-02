package com.huotu.loanmarket.service.repository.carrier;

import com.huotu.loanmarket.service.entity.carrier.ConsumeBill;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author luyuanyuan on 2018/1/30.
 */
public interface ConsumeBillRepository extends JpaRepository<ConsumeBill, Long> {
    List<ConsumeBill> findByOrderId(String orderId);
}

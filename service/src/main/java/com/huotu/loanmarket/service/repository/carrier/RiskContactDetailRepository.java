package com.huotu.loanmarket.service.repository.carrier;

import com.huotu.loanmarket.service.entity.carrier.RiskContactDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author luyuanyuan on 2018/1/30.
 */
public interface RiskContactDetailRepository extends JpaRepository<RiskContactDetail, Long> {

    List<RiskContactDetail> findByOrderId(String orderId);
}

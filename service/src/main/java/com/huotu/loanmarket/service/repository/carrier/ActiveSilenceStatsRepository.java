package com.huotu.loanmarket.service.repository.carrier;

import com.huotu.loanmarket.service.entity.carrier.ActiveSilenceStats;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author luyuanyuan on 2018/1/30.
 */
public interface ActiveSilenceStatsRepository extends JpaRepository<ActiveSilenceStats, Long> {
    ActiveSilenceStats findByOrderId(String orderId);
}

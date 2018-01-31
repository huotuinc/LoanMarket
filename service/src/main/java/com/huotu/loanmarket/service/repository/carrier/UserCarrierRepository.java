package com.huotu.loanmarket.service.repository.carrier;

import com.huotu.loanmarket.service.entity.carrier.UserCarrier;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author luyuanyuan on 2018/1/30.
 */
public interface UserCarrierRepository extends JpaRepository<UserCarrier, Integer> {
    UserCarrier findByOrderIdAndMerchantId(String orderId, Integer merchantId);
}

package com.huotu.loanmarket.service.repository.ds;

import com.huotu.loanmarket.service.entity.ds.Receiver;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author luyuanyuan on 2018/2/1.
 */
public interface ReceiverRepository extends JpaRepository<Receiver, Long> {
    List<Receiver> findByOrderId(String orderId);
}

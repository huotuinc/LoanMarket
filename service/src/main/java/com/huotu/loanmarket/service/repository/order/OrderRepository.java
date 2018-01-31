package com.huotu.loanmarket.service.repository.order;

import com.huotu.loanmarket.service.entity.order.Order;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author luyuanyuan on 2018/1/31.
 */
public interface OrderRepository extends JpaRepository<Order, String> {

}

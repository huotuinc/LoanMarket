package com.huotu.loanmarket.service.service.order;

import com.huotu.loanmarket.service.entity.order.Order;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author hxh
 * @Date 2018/2/1 14:32
 */
public interface OrderService {
    /**
     * 查询
     *
     * @param orderId
     * @return
     */
    Order findByOrderId(String orderId);

    /**
     * 保存
     *
     * @param order
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    Order save(Order order);
}

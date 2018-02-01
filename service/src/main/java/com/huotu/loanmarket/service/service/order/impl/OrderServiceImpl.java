package com.huotu.loanmarket.service.service.order.impl;

import com.huotu.loanmarket.service.entity.order.Order;
import com.huotu.loanmarket.service.repository.order.OrderRepository;
import com.huotu.loanmarket.service.service.order.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author hxh
 * @Date 2018/2/1 14:33
 */
@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderRepository orderRepository;

    @Override
    public Order findByOrderId(String orderId) {
        return orderRepository.findOne(orderId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Order save(Order order) {
        return orderRepository.save(order);
    }
}

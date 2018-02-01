package com.huotu.loanmarket.service.service.order.impl;

import com.huotu.loanmarket.common.Constant;
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

    @Override
    public String authenticationUrl(Order order) {
        switch (order.getOrderType().getCode()) {
            case 2:
                return String.format("https://open.shujumohe.com/box/yys?box_token=3A05206C0D654CABB59E567FCFC2791F&real_name=%s&identity_code=%s&user_mobile=%s&passback_params=%s", order.getRealName(), order.getIdCardNo(), order.getMobile(), order.getOrderId() + ",1,"+ Constant.YYS);
            case 3:
                return String.format("https://open.shujumohe.com/box/yys?box_token=3A05206C0D654CABB59E567FCFC2791F&real_name=%s&identity_code=%s&user_mobile=%s&passback_params=%s", order.getRealName(), order.getIdCardNo(), order.getMobile(), order.getOrderId() + ",1,"+ Constant.DS);
            case 4:
                return String.format("https://open.shujumohe.com/box/yys?box_token=3A05206C0D654CABB59E567FCFC2791F&real_name=%s&identity_code=%s&user_mobile=%s&passback_params=%s", order.getRealName(), order.getIdCardNo(), order.getMobile(), order.getOrderId() + ",1,"+ Constant.DS);
            default:
                return null;
        }
    }
}

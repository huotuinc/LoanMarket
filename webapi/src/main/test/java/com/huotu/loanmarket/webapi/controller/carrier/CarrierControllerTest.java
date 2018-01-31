package com.huotu.loanmarket.webapi.controller.carrier;

import com.huotu.loanmarket.service.entity.order.Order;
import com.huotu.loanmarket.service.repository.order.OrderRepository;
import com.huotu.loanmarket.service.service.carrier.UserCarrierService;
import com.huotu.loanmarket.webapi.base.BaseTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author luyuanyuan on 2018/1/31.
 */
public class CarrierControllerTest extends BaseTest {

    @Autowired
    private UserCarrierService userCarrierService;
    @Autowired
    private OrderRepository orderRepository;

    @Test
    public void queryResult() throws Exception {

        Order order = new Order();
        order.setOrderId(String.valueOf(System.currentTimeMillis()));
        orderRepository.saveAndFlush(order);
        String taskId = "TASKYYS100000201801151643430331920947";
        userCarrierService.queryResult(taskId,order.getOrderId(),1);
    }

}
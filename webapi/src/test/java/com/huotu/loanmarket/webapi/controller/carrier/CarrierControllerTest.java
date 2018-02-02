package com.huotu.loanmarket.webapi.controller.carrier;

import com.huotu.loanmarket.common.utils.JsonUtils;
import com.huotu.loanmarket.service.entity.carrier.ConsumeBill;
import com.huotu.loanmarket.service.entity.carrier.RiskContactDetail;
import com.huotu.loanmarket.service.entity.carrier.RiskContactStats;
import com.huotu.loanmarket.service.model.carrier.UserCarrierVo;
import com.huotu.loanmarket.service.repository.carrier.ConsumeBillRepository;
import com.huotu.loanmarket.service.repository.carrier.RiskContactDetailRepository;
import com.huotu.loanmarket.service.repository.carrier.RiskContactStatsRepository;
import com.huotu.loanmarket.service.repository.order.OrderRepository;
import com.huotu.loanmarket.service.service.carrier.UserCarrierService;
import com.huotu.loanmarket.webapi.controller.base.BaseTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author luyuanyuan on 2018/1/31.
 */
public class CarrierControllerTest extends BaseTest {

    @Autowired
    private UserCarrierService userCarrierService;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private RiskContactStatsRepository riskContactStatsRepository;
    @Autowired
    private RiskContactDetailRepository riskContactDetailRepository;
    @Autowired
    private ConsumeBillRepository consumeBillRepository;

    private final String orderId = "1517563312092";
    
    @Test
    public void queryResult() throws Exception {

//        Order order = new Order();
//        order.setOrderId(String.valueOf(System.currentTimeMillis()));
//        orderRepository.saveAndFlush(order);
//        String taskId = "TASKYYS100000201801151643430331920947";
        String taskId = "TASKYYS100000201801301118560711030451";
        userCarrierService.queryResult(taskId,orderId,1);
    }

    @Test
    public void carrierShow() {
        UserCarrierVo userCarrierVo = userCarrierService.carrierShow(orderId);
        System.out.println("<========="+ JsonUtils.objectToJson(userCarrierVo)+"=========>");

    }

    @Test
    public void riskContactList() {
        List<RiskContactStats> contactStatsList = riskContactStatsRepository.findByOrderId(orderId);
        System.out.println("<========="+ JsonUtils.objectToJson(contactStatsList)+"=========>");
    }

    @Test
    public void riskContactDetailList() {
        List<RiskContactDetail> riskContactDetailList = riskContactDetailRepository.findByOrderId(orderId);
        System.out.println("<========="+ JsonUtils.objectToJson(riskContactDetailList)+"=========>");
    }

    @Test
    public void consumeBillList() {
        List<ConsumeBill> consumeBillList = consumeBillRepository.findByOrderId(orderId);
        System.out.println("<========="+ JsonUtils.objectToJson(consumeBillList)+"=========>");
    }

}
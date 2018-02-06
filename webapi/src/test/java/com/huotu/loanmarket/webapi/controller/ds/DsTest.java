package com.huotu.loanmarket.webapi.controller.ds;

import com.huotu.loanmarket.common.utils.JsonUtils;
import com.huotu.loanmarket.service.model.ds.DsOrderVo;
import com.huotu.loanmarket.service.model.ds.DsVo;
import com.huotu.loanmarket.service.repository.ds.DsOrderRepository;
import com.huotu.loanmarket.service.repository.order.OrderRepository;
import com.huotu.loanmarket.service.service.ds.DsService;
import com.huotu.loanmarket.webapi.controller.base.BaseTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.ExecutionException;

/**
 * @author luyuanyuan on 2018/1/31.
 */
public class DsTest extends BaseTest {

    @Autowired
    private DsService dsService;
    @Autowired
    private DsOrderRepository dsOrderRepository;
    @Autowired
    private OrderRepository orderRepository;

    private final String orderId = "1517563312092";

    @Test
    public void queryDsResult() throws Exception {

//        Order order = new Order();
//        order.setOrderId(String.valueOf(System.currentTimeMillis()));
//        orderRepository.saveAndFlush(order);
//        String taskId = "TASKYYS100000201801151643430331920947";
        String taskId = "TASKDS000103201801291138560751361175";
        dsService.queryResult(taskId,orderId,1);
    }

    @Test
    public void dsShow() throws ExecutionException, InterruptedException {
        DsVo dsVo = dsService.dsShow(orderId);
        System.out.println("<========="+JsonUtils.objectToJson(dsVo)+"=========>");

    }

    @Test
    public void dsOrder() throws ExecutionException, InterruptedException {
        DsOrderVo stats = dsOrderRepository.stats(orderId);
        System.out.println("<========="+JsonUtils.objectToJson(stats)+"=========>");

    }

}
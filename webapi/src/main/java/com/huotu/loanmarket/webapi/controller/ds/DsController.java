package com.huotu.loanmarket.webapi.controller.ds;

import com.huotu.loanmarket.common.utils.ApiResult;
import com.huotu.loanmarket.service.entity.ds.Receiver;
import com.huotu.loanmarket.service.entity.order.Order;
import com.huotu.loanmarket.service.enums.AppCode;
import com.huotu.loanmarket.service.model.ds.DsOrderVo;
import com.huotu.loanmarket.service.model.ds.DsVo;
import com.huotu.loanmarket.service.repository.ds.DsOrderRepository;
import com.huotu.loanmarket.service.repository.ds.ReceiverRepository;
import com.huotu.loanmarket.service.service.ds.DsService;
import com.huotu.loanmarket.service.service.order.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

/**
 * @author luyuanyuan on 2018/2/2.
 */
@RequestMapping("/api/ds")
@Controller
public class DsController {

    @Autowired
    private OrderService orderService;
    @Autowired
    private DsService dsService;
    @Autowired
    private DsOrderRepository dsOrderRepository;
    @Autowired
    private ReceiverRepository receiverRepository;

    /**
     * 电商展示数据
     * @param userId
     * @param orderId
     * @return
     * @throws ExecutionException
     * @throws InterruptedException
     */
    @RequestMapping("/dsShow")
    @ResponseBody
    public ApiResult dsShow(@RequestHeader(value = "userId") Long userId, String orderId) throws ExecutionException, InterruptedException {
        Order order = orderService.findByUserIdAndOrderId(userId,orderId);
        if(order == null) {
            return ApiResult.resultWith(AppCode.PARAMETER_ERROR);
        }
        DsVo dsVo = dsService.dsShow(orderId);
        return ApiResult.resultWith(AppCode.SUCCESS,dsVo);
    }

    /**
     * 电商订单统计
     * @param userId
     * @param orderId
     * @return
     */
    @RequestMapping("/dsOrder")
    @ResponseBody
    public ApiResult dsOrder(@RequestHeader(value = "userId") Long userId, String orderId) {
        Order order = orderService.findByUserIdAndOrderId(userId,orderId);
        if(order == null) {
            return ApiResult.resultWith(AppCode.PARAMETER_ERROR);
        }

        DsOrderVo dsOrderVo = dsOrderRepository.stats(orderId);
        return ApiResult.resultWith(AppCode.SUCCESS,dsOrderVo);
    }

    /**
     * 电商收货地址展示
     * @param userId
     * @param orderId
     * @return
     */
    @RequestMapping("/receiverList")
    @ResponseBody
    public ApiResult receiverList(@RequestHeader(value = "userId") Long userId, String orderId) {
        Order order = orderService.findByUserIdAndOrderId(userId,orderId);
        if(order == null) {
            return ApiResult.resultWith(AppCode.PARAMETER_ERROR);
        }

        List<Receiver> receiverList = receiverRepository.findByOrderId(orderId);
        List<String> list = receiverList.stream().map(Receiver::getArea).collect(Collectors.toList());
        return ApiResult.resultWith(AppCode.SUCCESS,list);
    }
}

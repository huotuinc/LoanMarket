package com.huotu.loanmarket.webapi.controller.ds;

import com.huotu.loanmarket.common.Constant;
import com.huotu.loanmarket.service.entity.ds.Receiver;
import com.huotu.loanmarket.service.entity.order.Order;
import com.huotu.loanmarket.service.model.ds.DsOrderVo;
import com.huotu.loanmarket.service.model.ds.DsVo;
import com.huotu.loanmarket.service.repository.ds.DsOrderRepository;
import com.huotu.loanmarket.service.repository.ds.ReceiverRepository;
import com.huotu.loanmarket.service.service.ds.DsService;
import com.huotu.loanmarket.service.service.order.OrderService;
import com.huotu.loanmarket.webapi.controller.exception.OrderNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

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
    public String dsShow(Long userId, String orderId,Model model) {
        checkParam(userId, orderId);
        DsVo dsVo = dsService.dsShow(orderId);
        model.addAttribute("ds",dsVo);
        model.addAttribute("orderId",orderId);
        model.addAttribute("userId",userId);
        return "report/dsInfo";
    }

    /**
     * 电商订单统计
     * @param userId
     * @param orderId
     * @return
     */
    @RequestMapping("/dsOrder")
    public String dsOrder(Long userId, String orderId,Model model) {
        checkParam(userId, orderId);
        DsOrderVo dsOrderVo = dsOrderRepository.stats(orderId);
        model.addAttribute("dsOrder",dsOrderVo);
        return "report/dsOrder";
    }

    /**
     * 电商收货地址展示
     * @param userId
     * @param orderId
     * @return
     */
    @RequestMapping("/receiverList")
    public String receiverList(Long userId, String orderId,Model model) {
        checkParam(userId, orderId);

        List<Receiver> receiverList = receiverRepository.findByOrderId(orderId);
        List<String> list = receiverList.stream().map(Receiver::getArea).collect(Collectors.toList());
        model.addAttribute("receiverList",list);
        return "report/receiverList";
    }

    private void checkParam(Long userId, String orderId) {
        Order order = orderService.findByOrderId(orderId);
        if(order == null || !order.getUser().getUserId().equals(userId)) {
            throw new OrderNotFoundException(Constant.ORDER_NOT_FOUND);
        }
    }
}

package com.huotu.loanmarket.web.controller.order;

import com.huotu.loanmarket.service.entity.order.Order;
import com.huotu.loanmarket.service.enums.OrderEnum;
import com.huotu.loanmarket.service.enums.UserAuthorizedStatusEnums;
import com.huotu.loanmarket.service.model.order.OrderSearchCondition;
import com.huotu.loanmarket.service.service.order.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.math.BigDecimal;

/**
 * @author luyuanyuan on 2018/2/7.
 */
@RequestMapping("/admin/order")
@Controller
public class OrderController {

    @Autowired
    private OrderService orderService;

    @RequestMapping("/list")
    public String orderList(OrderSearchCondition orderSearchCondition, Model model) {
        Page<Order> orderPage = orderService.findAll(orderSearchCondition);
        BigDecimal totalPayAmount = orderService.sumPayAmount(orderSearchCondition);
        model.addAttribute("orderPage", orderPage);
        model.addAttribute("orderType", OrderEnum.OrderType.values());
        model.addAttribute("payStatus", OrderEnum.PayStatus.values());
        model.addAttribute("userAuthorizedStatusEnums", UserAuthorizedStatusEnums.values());
        model.addAttribute("totalPayAmount", totalPayAmount);
        model.addAttribute("pageIndex", orderSearchCondition.getPageIndex());
        return "order/order_list";
    }
}

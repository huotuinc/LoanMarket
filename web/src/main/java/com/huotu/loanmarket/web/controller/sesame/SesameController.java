package com.huotu.loanmarket.web.controller.sesame;

import com.huotu.loanmarket.service.entity.order.Order;
import com.huotu.loanmarket.service.entity.sesame.Industry;
import com.huotu.loanmarket.service.service.order.OrderService;
import com.huotu.loanmarket.service.service.sesame.SesameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping("/sesame")
@Controller
public class SesameController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private SesameService sesameService;

    @RequestMapping("/getReport")
    public String getSesameReport(String orderId, Model model) {
        Order order = orderService.findByOrderId(orderId);
        List<Industry> industryList = sesameService.findByUserIdAndOrderId(order.getUser().getUserId(), orderId);
        model.addAttribute("industryList", industryList);
        return "sesame/sesameInfo";
    }
}

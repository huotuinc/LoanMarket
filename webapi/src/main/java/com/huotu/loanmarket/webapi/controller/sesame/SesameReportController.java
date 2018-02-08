package com.huotu.loanmarket.webapi.controller.sesame;

import com.huotu.loanmarket.service.entity.order.Order;
import com.huotu.loanmarket.service.entity.sesame.Industry;
import com.huotu.loanmarket.service.service.order.OrderService;
import com.huotu.loanmarket.service.service.sesame.SesameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/api/sesameReport")
public class SesameReportController {
    @Autowired
    private SesameService sesameService;
    @Autowired
    private OrderService orderService;

    @RequestMapping("/getSesameReport")
    public String getSesameReport(String orderId, Model model) {
        Order order = orderService.findByOrderId(orderId);
        List<Industry> industryList = sesameService.findByUserIdAndOrderId(order.getUser().getUserId(), orderId);
        model.addAttribute("industryList", industryList);
        return "report/sesame";
    }
}

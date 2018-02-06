package com.huotu.loanmarket.web.controller.ds;

import com.huotu.loanmarket.service.entity.ds.Receiver;
import com.huotu.loanmarket.service.model.ds.DsOrderVo;
import com.huotu.loanmarket.service.model.ds.DsVo;
import com.huotu.loanmarket.service.repository.ds.DsOrderRepository;
import com.huotu.loanmarket.service.repository.ds.ReceiverRepository;
import com.huotu.loanmarket.service.service.ds.DsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author luyuanyuan on 2018/2/2.
 */
@RequestMapping("/admin/ds")
@Controller
public class DsController {

    @Autowired
    private DsService dsService;
    @Autowired
    private DsOrderRepository dsOrderRepository;
    @Autowired
    private ReceiverRepository receiverRepository;

    /**
     * 电商展示数据
     * @param orderId
     * @return
     */
    @RequestMapping("/dsShow")
    public String dsShow(String orderId,Model model) {
        DsVo dsVo = dsService.dsShow(orderId);
        DsOrderVo dsOrderVo = dsOrderRepository.stats(orderId);
        List<Receiver> receiverList = receiverRepository.findByOrderId(orderId);
        List<String> list = receiverList.stream().map(Receiver::getArea).collect(Collectors.toList());
        model.addAttribute("receiverList",list);
        model.addAttribute("dsOrder",dsOrderVo);
        model.addAttribute("ds",dsVo);
        return "ds/dsInfo";
    }

}

package com.huotu.loanmarket.web.controller.carrier;

import com.huotu.loanmarket.service.entity.carrier.ConsumeBill;
import com.huotu.loanmarket.service.entity.carrier.RiskContactDetail;
import com.huotu.loanmarket.service.entity.carrier.RiskContactStats;
import com.huotu.loanmarket.service.model.carrier.UserCarrierVo;
import com.huotu.loanmarket.service.repository.carrier.ConsumeBillRepository;
import com.huotu.loanmarket.service.repository.carrier.RiskContactDetailRepository;
import com.huotu.loanmarket.service.repository.carrier.RiskContactStatsRepository;
import com.huotu.loanmarket.service.service.carrier.UserCarrierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Comparator;
import java.util.List;

/**
 * 运营商相关
 * @author luyuanyuan on 2017/11/22.
 */
@RequestMapping("/admin/carrier")
@Controller
public class CarrierController {

    @Autowired
    private UserCarrierService userCarrierService;
    @Autowired
    private RiskContactStatsRepository riskContactStatsRepository;
    @Autowired
    private RiskContactDetailRepository riskContactDetailRepository;
    @Autowired
    private ConsumeBillRepository consumeBillRepository;

    /**
     * 运营商展示数据
     * @param orderId
     * @return
     */
    @RequestMapping("/carrierShow")
    public String carrierShow(String orderId,Model model) {

        UserCarrierVo userCarrierVo = userCarrierService.carrierShow(orderId);
        List<RiskContactStats> contactStatsList = riskContactStatsRepository.findByOrderId(orderId);
        List<RiskContactDetail> riskContactDetailList = riskContactDetailRepository.findByOrderId(orderId);
        List<ConsumeBill> consumeBillList = consumeBillRepository.findByOrderId(orderId);
        consumeBillList.sort(Comparator.comparing(ConsumeBill::getMonth));

        model.addAttribute("consumeBillList",consumeBillList);
        model.addAttribute("riskContactDetailList",riskContactDetailList);
        model.addAttribute("riskContactList",contactStatsList);
        model.addAttribute("carrier",userCarrierVo);
        return "carrier/carrierInfo";
    }

}

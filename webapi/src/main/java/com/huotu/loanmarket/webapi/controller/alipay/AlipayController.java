/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼在地图中查看
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2016-2018. All rights reserved.
 */

package com.huotu.loanmarket.webapi.controller.alipay;

import com.huotu.loanmarket.common.Constant;
import com.huotu.loanmarket.common.utils.ApiResult;
import com.huotu.loanmarket.service.entity.order.Order;
import com.huotu.loanmarket.service.entity.user.User;
import com.huotu.loanmarket.service.enums.AppCode;
import com.huotu.loanmarket.service.enums.OrderEnum;
import com.huotu.loanmarket.service.service.order.OrderService;
import com.huotu.loanmarket.service.service.thirdpay.QuickPaymentContext;
import com.huotu.loanmarket.thirdpay.alipay.model.AlipayConfig;
import com.huotu.loanmarket.thirdpay.alipay.service.AlipayHelper;
import com.huotu.loanmarket.thirdpay.common.AbstractPayConfig;
import com.huotu.loanmarket.webapi.controller.order.OrderController;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.interceptor.ExcludeDefaultInterceptors;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author guomw
 * @date 01/02/2018
 */
@Controller
@RequestMapping(value = "/api/alipay", method = RequestMethod.POST)
public class AlipayController {
    private static final Log log = LogFactory.getLog(OrderController.class);
    private static final String TRADE_FINISHED = "TRADE_FINISHED";
    private static final String TRADE_SUCCESS = "TRADE_SUCCESS";


    @Autowired
    private OrderService orderService;
    @Autowired
    private QuickPaymentContext quickPaymentContext;


    /**
     * 支付宝通知处理
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/notify", method = RequestMethod.POST)
    @ExcludeDefaultInterceptors
    public void payNotify(HttpServletRequest request, HttpServletResponse response) {
        try {
            //获取所有参数并记录日志
            Map<String, String[]> requestMap = request.getParameterMap();
            Map<String, String> paramMap = new HashMap<>(requestMap.size());
            requestMap.forEach((key, value) -> paramMap.put(key, value[0]));
            List<String> paramList = new ArrayList<>();
            paramMap.forEach((key, value) -> paramList.add(key + "=" + value));
            String paramStr = String.join("&", paramList);
            log.info("aliPayNotify参数：" + paramStr);

            String outTradeNo = paramMap.get("out_trade_no");
            String totalFee = paramMap.get("total_amount");
            String tradeNo = paramMap.get("trade_no");
            String tradeStatus = paramMap.get("trade_status");

            Order unifiedOrder = orderService.findByOrderId(outTradeNo);
            if (unifiedOrder == null) {
                //这里给true，让支付宝不要再做无谓的通知了
                this.outputResult(true, MessageFormat.format("统一订单:{0}不存在", outTradeNo), response);
                return;
            }

            if (unifiedOrder.getPayStatus().equals(OrderEnum.PayStatus.PAY_SUCCESS)) {
                this.outputResult(true, MessageFormat.format("统一订单:{0}已支付", outTradeNo), response);
                return;
            }

            AbstractPayConfig payConfig = quickPaymentContext.getCurrent(OrderEnum.PayType.ALIPAY).getPayConfig(Constant.MERCHANT_ID);
            if (payConfig == null) {
                this.outputResult(true, MessageFormat.format("支付方式({0})不存在", OrderEnum.PayType.ALIPAY.getName()), response);
                return;
            }
            boolean verifyResult = AlipayHelper.rsaCheck(paramMap, ((AlipayConfig) payConfig).getAliPayPublicKey());
            if (!verifyResult) {
                this.outputResult(false, "验签失败", response);
                return;
            }
            unifiedOrder.setTradeNo(tradeNo);
            unifiedOrder.setOnlineAmount(new BigDecimal(totalFee));
            unifiedOrder.setPayType(OrderEnum.PayType.ALIPAY);
            User user = unifiedOrder.getUser();
            if (TRADE_FINISHED.equals(tradeStatus)) {
                orderService.paid(unifiedOrder, user);
            } else if (TRADE_SUCCESS.equals(tradeStatus)) {
                orderService.paid(unifiedOrder, user);
            }
            this.outputResult(true, MessageFormat.format("单号：{0}支付成功", outTradeNo), response);
        } catch (Exception e) {
            this.outputResult(false, e.getMessage(), response);
        }
    }

    private void outputResult(boolean flag, String logContent, HttpServletResponse response) {
        String result = flag ? "success" : "fail";
        if (!StringUtils.isEmpty(logContent)) {
            if (flag) {
                log.info(MessageFormat.format("aliPayNotify(info):{0}", logContent));
            } else {
                log.error(MessageFormat.format("aliPayNotify(error)->{0}", logContent));
            }
        }
        response.setContentType("text/html;charset=utf-8");
        try {
            response.getWriter().write(result);
        } catch (IOException e) {
            log.error("outputResult异常：" + e.getMessage(), e);
        }
    }

    @Autowired
    private Environment env;

    /***
     * 测试。只在开发环境下执行
     * @param orderId
     * @return
     */
    @RequestMapping(value = "/test", method = RequestMethod.GET)
    @ResponseBody
    public ApiResult test(String orderId) {
        if (env.acceptsProfiles(Constant.PROFILE_DEV, Constant.PROFILE_UN_CHECK)) {
            Order unifiedOrder = orderService.findByOrderId(orderId);
            unifiedOrder.setTradeNo(orderId);
            unifiedOrder.setOnlineAmount(unifiedOrder.getPayAmount());
            unifiedOrder.setPayType(OrderEnum.PayType.ALIPAY);
            User user = unifiedOrder.getUser();
            orderService.paid(unifiedOrder, user);
        }
        return ApiResult.resultWith(AppCode.SUCCESS);
    }
}

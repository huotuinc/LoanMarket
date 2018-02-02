/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼在地图中查看
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2016-2018. All rights reserved.
 */

package com.huotu.loanmarket.service.service.order.impl;

import com.huotu.loanmarket.common.Constant;
import com.huotu.loanmarket.common.utils.RandomUtils;
import com.huotu.loanmarket.service.aop.BusinessSafe;
import com.huotu.loanmarket.service.entity.order.Order;
import com.huotu.loanmarket.service.entity.order.OrderLog;
import com.huotu.loanmarket.service.entity.user.User;
import com.huotu.loanmarket.service.enums.ConfigParameter;
import com.huotu.loanmarket.service.enums.MerchantConfigEnum;
import com.huotu.loanmarket.service.enums.OrderEnum;
import com.huotu.loanmarket.service.enums.UserAuthorizedStatusEnums;
import com.huotu.loanmarket.service.model.order.PayReturnVo;
import com.huotu.loanmarket.service.model.order.SubmitOrderInfo;
import com.huotu.loanmarket.service.repository.order.OrderLogRepository;
import com.huotu.loanmarket.service.repository.order.OrderRepository;
import com.huotu.loanmarket.service.service.merchant.MerchantCfgService;
import com.huotu.loanmarket.service.service.order.OrderService;
import com.huotu.loanmarket.service.service.user.UserService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.Map;

/**
 * @author guomw
 * @date 01/02/2018
 */
@Service
public class OrderServiceImpl implements OrderService {

    private static final Log log = LogFactory.getLog(OrderServiceImpl.class);
    @Autowired
    private UserService userService;
    @Autowired
    private MerchantCfgService merchantCfgService;
    @Autowired
    private OrderLogRepository orderLogRepository;
    @Autowired
    private OrderRepository orderRepository;

    /**
     * 创建订单
     *
     * @param submitOrderInfo
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @BusinessSafe
    @Override
    public Order create(SubmitOrderInfo submitOrderInfo) {
        return submitOrder(submitOrderInfo);
    }

    /**
     * 确认订单
     * @param submitOrderInfo
     * @return
     */
    @Override
    public Order checkout(SubmitOrderInfo submitOrderInfo) {
        return getTradeOrder(submitOrderInfo);
    }

    @Override
    public PayReturnVo getPayReturnInfo(String orderNo) {
        Order unifiedOrder = this.findByOrderId(orderNo);
        if (unifiedOrder == null) {
            return null;
        }
        PayReturnVo payReturnVo = new PayReturnVo();
        payReturnVo.setRedirectText("返回");
        payReturnVo.setRedirectUrl(unifiedOrder.getRedirectUrl());
        payReturnVo.setUserId(unifiedOrder.getUser().getUserId().intValue());
        payReturnVo.setUnifiedOrderNo(orderNo);
        return payReturnVo;
    }

    /**
     * 提交订单
     *
     * @param submitOrderInfo
     * @return
     */
    private Order submitOrder(SubmitOrderInfo submitOrderInfo) {
        User user = userService.findByMerchantIdAndUserId(Constant.MERCHANT_ID, submitOrderInfo.getUserId());
        Order order=getTradeOrder(submitOrderInfo);
        order.setUser(user);
        order.setOrderId(RandomUtils.randomDateTimeString());
        //设置第三方授权页面地址
        order.setThirdAuthUrl(authenticationUrl(order));
        order = orderRepository.save(order);

        OrderLog log = new OrderLog();
        log.setLogType(OrderEnum.LogType.CREATE_ORDER);
        log.setLogText("创建订单");
        log.setMerchant(order.getMerchant());
        log.setOpName(order.getUser().getUserName());
        log.setOrderId(order.getOrderId());
        log.setUserId(submitOrderInfo.getUserId());
        log.setResult(1);
        log.setActTime(LocalDateTime.now());
        orderLogRepository.save(log);
        return order;
    }

    /***
     * 封装交易订单信息
     * @param submitOrderInfo
     * @return
     */
    private Order getTradeOrder(SubmitOrderInfo submitOrderInfo){
        Order order = new Order();
        order.setMobile(submitOrderInfo.getMobile());
        order.setRealName(submitOrderInfo.getName());
        order.setIdCardNo(submitOrderInfo.getIdCardNo());
        order.setOrderType(submitOrderInfo.getOrderType());
        order.setCreateTime(LocalDateTime.now());
        Map<String, String> items = merchantCfgService.getConfigItem(Constant.MERCHANT_ID, MerchantConfigEnum.AUTH_FEE);
        String money = "10";
        switch (submitOrderInfo.getOrderType()) {
            //行业黑名单
            case BACKLIST_BUS:
                money = items.get(ConfigParameter.AuthFeeParameter.BACKLIST_BUS_FEE.getKey());
                break;
            //金融黑名单
            case BACKLIST_FINANCE:
                money = items.get(ConfigParameter.AuthFeeParameter.BACKLIST_FINANCE_FEE.getKey());
                break;
            //运营商
            case CARRIER:
                money = items.get(ConfigParameter.AuthFeeParameter.CARRIER_FEE.getKey());
                break;
            //淘宝
            case TAOBAO:
                money = items.get(ConfigParameter.AuthFeeParameter.TAOBAO_FEE.getKey());
                break;
            //京东
            case JINGDONG:
                money = items.get(ConfigParameter.AuthFeeParameter.JINGDONG_FEE.getKey());
                break;
            default:
                break;
        }
        if (money == null || StringUtils.isEmpty(money)) {
            money = "10";
        }
        order.setPayAmount(BigDecimal.valueOf(Long.parseLong(money)));
        return order;
    }


    @Override
    public Order findByOrderId(String orderId) {
        return orderRepository.findOne(orderId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateOrderPayStatus(String orderId, OrderEnum.PayStatus payStatus) {
        orderRepository.updateOrderPayStatusByOrderId(orderId, payStatus);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateOrderStatus(String orderId, OrderEnum.OrderStatus orderStatus) {
        orderRepository.updateOrderStatusByOrderId(orderId, orderStatus);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateOrderAuthStatus(String orderId, UserAuthorizedStatusEnums authStatus) {
        orderRepository.updateOrderAuthStatusByOrderId(orderId, authStatus);

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateOrderAuthCount(String orderId) {
        orderRepository.updateOrderAuthCountByOrderId(orderId);

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Order save(Order order) {
        return orderRepository.save(order);
    }


    @Override
    public String authenticationUrl(Order order) {
        String url="";
        switch (order.getOrderType()) {
            case BACKLIST_FINANCE:
                url=String.format("https://open.shujumohe.com/box/yys?box_token=3A05206C0D654CABB59E567FCFC2791F&real_name=%s&identity_code=%s&user_mobile=%s&passback_params=%s", order.getRealName(), order.getIdCardNo(), order.getMobile(), order.getOrderId() + ",1,"+ Constant.YYS);
                break;
            case CARRIER:
                break;
            case JINGDONG:
                break;
            case TAOBAO:
                break;
            case BACKLIST_BUS:
                break;
            default:
                break;
        }

        return url;
    }

    /**
     * 完成支付
     * @param unifiedOrder
     * @param user
     */
    @Override
    public void paid(Order unifiedOrder, User user) {
        //0、检查支付金额是不是足额
        if (unifiedOrder.getOnlineAmount().compareTo(unifiedOrder.getPayAmount()) == -1) {
            log.error(MessageFormat.format("统一支付订单异常：需付：{0}，实付：{1}，单号：{2}，交易号：{3}",
                    unifiedOrder.getPayAmount(), unifiedOrder.getOnlineAmount(), unifiedOrder.getOrderId(), unifiedOrder.getTradeNo()));
            return;
        }
        //1、更改订单状态
        unifiedOrder.setPayTime(LocalDateTime.now());
        unifiedOrder.setPayStatus(OrderEnum.PayStatus.PAY_SUCCESS);
        unifiedOrder.setAuthStatus(UserAuthorizedStatusEnums.AUTH_ING);
        //TODO:系统配置读取
        unifiedOrder.setAuthCount(3);
        orderRepository.save(unifiedOrder);
    }
}

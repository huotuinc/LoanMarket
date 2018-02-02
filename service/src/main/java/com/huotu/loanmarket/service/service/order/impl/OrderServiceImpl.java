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
     * @param userId
     * @param mobile
     * @param name
     * @param idCardNo
     * @param orderType
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @BusinessSafe
    @Override
    public Order create(Long userId, String mobile, String name, String idCardNo, OrderEnum.OrderType orderType) {
        SubmitOrderInfo submitOrderInfo = new SubmitOrderInfo();
        submitOrderInfo.setUserId(userId);
        submitOrderInfo.setOrderType(orderType);
        submitOrderInfo.setName(name);
        submitOrderInfo.setIdCardNo(idCardNo);
        return submitOrder(submitOrderInfo);
    }

    /**
     * 创建订单
     *
     * @param userId
     * @param orderType
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @BusinessSafe
    @Override
    public Order create(Long userId, OrderEnum.OrderType orderType) {
        SubmitOrderInfo submitOrderInfo = new SubmitOrderInfo();
        submitOrderInfo.setUserId(userId);
        submitOrderInfo.setOrderType(orderType);
        return submitOrder(submitOrderInfo);
    }

    /**
     * 提交订单
     *
     * @param submitOrderInfo
     * @return
     */
    private Order submitOrder(SubmitOrderInfo submitOrderInfo) {

        User user = userService.findByMerchantIdAndUserId(Constant.MERCHANT_ID, submitOrderInfo.getUserId());

        Order order = new Order();
        order.setOrderId(RandomUtils.randomDateTimeString());
        order.setUserId(user);
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
        order.setThirdAuthUrl("");
        order.setPayAmount(BigDecimal.valueOf(Long.parseLong(money)));

        order = orderRepository.save(order);

        OrderLog log = new OrderLog();
        log.setLogType(OrderEnum.LogType.CREATE_ORDER);
        log.setLogText("创建订单");
        log.setMerchant(order.getMerchant());
        log.setOpName(user.getUserName());
        log.setOrderId(order.getOrderId());
        log.setUserId(submitOrderInfo.getUserId());
        log.setResult(1);
        log.setActTime(LocalDateTime.now());
        orderLogRepository.save(log);

        return order;
    }


    @Override
    public Order findByOrderId(String orderId) {
        return null;
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
        // h5方式只支持运营商和京东对接，淘宝改用sdk对接
        switch (order.getOrderType().getCode()) {
            case 2:
                return String.format("https://open.shujumohe.com/box/yys?box_token=3A05206C0D654CABB59E567FCFC2791F&real_name=%s&identity_code=%s&user_mobile=%s&passback_params=%s", order.getRealName(), order.getIdCardNo(), order.getMobile(), order.getOrderId() + ",1,"+ Constant.YYS);
            case 4:
                return String.format("https://open.shujumohe.com/box/jd?box_token=5884F7B994A7445E9B6C89CA2D2942AA&passback_params=%s", order.getOrderId() + ",1,"+ Constant.DS);
            default:
                return null;
        }
    }
}

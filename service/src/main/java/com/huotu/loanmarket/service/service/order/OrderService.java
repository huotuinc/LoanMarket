/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼在地图中查看
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2016-2018. All rights reserved.
 */

package com.huotu.loanmarket.service.service.order;

import com.huotu.loanmarket.service.aop.BusinessSafe;
import com.huotu.loanmarket.service.entity.order.Order;
import com.huotu.loanmarket.service.entity.user.User;
import com.huotu.loanmarket.service.enums.OrderEnum;
import com.huotu.loanmarket.service.enums.UserAuthorizedStatusEnums;
import com.huotu.loanmarket.service.model.order.*;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author guomw
 * @date 01/02/2018
 */
public interface OrderService {

    /**
     * 创建订单
     * @param submitOrderInfo
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @BusinessSafe
    Order create(SubmitOrderInfo submitOrderInfo);

    /**
     * 确认订单
     * @param submitOrderInfo
     * @return
     */
    ApiCheckoutResultVo checkout(SubmitOrderInfo submitOrderInfo);
    /**
     * 得到支付返回页面上的实体
     * @param orderNo
     * @return
     */
    PayReturnVo getPayReturnInfo(String orderNo);

    /**
     * 查询
     *
     * @param orderId
     * @return
     */
    Order findByOrderId(String orderId);

    /**
     * 获取订单列表
     * @param userId
     * @param pageIndex
     * @param pageSize
     * @param authStatus
     * @return
     */
    List<ApiOrderInfoVo> getList(Long userId, int pageIndex, int pageSize,@RequestParam(required = false) UserAuthorizedStatusEnums authStatus);


    /**
     * 更新订单支付状态
     * @param orderId
     * @param payStatus
     */
    @Transactional(rollbackFor = Exception.class)
    void updateOrderPayStatus(String orderId,OrderEnum.PayStatus payStatus);

    /**
     * 更新订单状态
     * @param orderId
     * @param orderStatus
     */
    @Transactional(rollbackFor = Exception.class)
    void updateOrderStatus(String orderId,OrderEnum.OrderStatus orderStatus);

    /**
     * 更新订单认证状态
     * @param orderId
     * @param authStatus
     */
    @Transactional(rollbackFor = Exception.class)
    void updateOrderAuthStatus(String orderId, UserAuthorizedStatusEnums authStatus);

    /**
     * 更新订单认证次数
     * @param orderId
     */
    @Transactional(rollbackFor = Exception.class)
    void updateOrderAuthCount(String orderId);

    /**
     * 保存
     *
     * @param order
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    Order save(Order order);

    /**
     * 根据订单返回第三方链接
     * @param order
     * @return
     */
    OrderThirdUrlInfo getOrderThirdUrl(Order order);


    /**
     * 完成支付
     * @param order
     * @param user
     */
    void paid(Order order, User user);

}

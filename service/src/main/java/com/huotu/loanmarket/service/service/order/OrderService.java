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
import com.huotu.loanmarket.service.model.order.ApiOrderCreateResultVo;
import com.huotu.loanmarket.service.model.order.PayReturnVo;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author guomw
 * @date 01/02/2018
 */
public interface OrderService {

    /**
     * 创建订单
     * @param userId
     * @param mobile
     * @param name
     * @param idCardNo
     * @param redirectUrl
     * @param orderType
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @BusinessSafe
    Order create(Long userId,String mobile,String name,String idCardNo,String redirectUrl, OrderEnum.OrderType orderType);

    /**
     * 创建订单
     * @param userId
     * @param redirectUrl
     * @param orderType
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @BusinessSafe
    Order create(Long userId, String redirectUrl,OrderEnum.OrderType orderType);

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
     * 根据订单返回第三方认证链接,不满足条件返回null
     * @param order
     * @return
     */
    String authenticationUrl(Order order);


    /**
     * 完成支付
     * @param order
     * @param user
     */
    void paid(Order order, User user);
}

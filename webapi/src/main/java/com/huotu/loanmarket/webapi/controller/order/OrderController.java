/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼在地图中查看
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2016-2018. All rights reserved.
 */

package com.huotu.loanmarket.webapi.controller.order;

import com.huotu.loanmarket.common.Constant;
import com.huotu.loanmarket.common.enums.EnumHelper;
import com.huotu.loanmarket.common.utils.ApiResult;
import com.huotu.loanmarket.common.utils.RegexUtils;
import com.huotu.loanmarket.service.entity.order.Order;
import com.huotu.loanmarket.service.enums.AppCode;
import com.huotu.loanmarket.service.enums.OrderEnum;
import com.huotu.loanmarket.service.enums.UserResultCode;
import com.huotu.loanmarket.service.model.order.ApiOrderCreateResultVo;
import com.huotu.loanmarket.service.model.order.SubmitOrderInfo;
import com.huotu.loanmarket.service.model.payconfig.PaymentBizParametersVo;
import com.huotu.loanmarket.service.service.order.OrderService;
import com.huotu.loanmarket.service.service.thirdpay.QuickPaymentContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

/**
 * @author guomw
 * @date 01/02/2018
 */
@Controller
@RequestMapping(value = "/api/order", method = RequestMethod.POST)
public class OrderController {

    private static final Log log = LogFactory.getLog(OrderController.class);

    @Autowired
    private OrderService orderService;
    @Autowired
    private QuickPaymentContext quickPaymentContext;

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
    @RequestMapping("/create")
    @ResponseBody
    public ApiResult create(@RequestHeader(value = Constant.APP_USER_ID_KEY, required = false, defaultValue = "0") Long userId,
                            @RequestParam(required = false, defaultValue = "") String mobile,
                            @RequestParam(required = false, defaultValue = "") String name,
                            @RequestParam(required = false, defaultValue = "") String idCardNo,
                            @RequestParam String redirectUrl,
                            Integer orderType) {
        try {
            OrderEnum.OrderType tradeTypeEnum = EnumHelper.getEnumType(OrderEnum.OrderType.class, orderType);
            if (tradeTypeEnum == null) {
                return ApiResult.resultWith(AppCode.ERROR, "不支持的交易类型");
            }

            if (!tradeTypeEnum.equals(OrderEnum.OrderType.JINGDONG) && !tradeTypeEnum.equals(OrderEnum.OrderType.TAOBAO)) {
                if (StringUtils.isEmpty(mobile) || !RegexUtils.checkMobile(mobile)) {
                    return ApiResult.resultWith(UserResultCode.CODE1);
                }
                if (StringUtils.isEmpty(idCardNo)) {
                    return ApiResult.resultWith(AppCode.PARAMETER_ERROR, "身份证号码不能为空");
                }

                if (StringUtils.isEmpty(name)) {
                    return ApiResult.resultWith(AppCode.PARAMETER_ERROR, "姓名不能为空");
                }
            }
            SubmitOrderInfo submitOrderInfo = new SubmitOrderInfo();
            submitOrderInfo.setUserId(userId);
            submitOrderInfo.setOrderType(tradeTypeEnum);
            submitOrderInfo.setName(name);
            submitOrderInfo.setMobile(mobile);
            submitOrderInfo.setIdCardNo(idCardNo);
            submitOrderInfo.setRedirectUrl(redirectUrl);
            Order order = orderService.create(submitOrderInfo);
            if (order != null && order.getPayStatus().equals(OrderEnum.PayStatus.NOT_PAY)) {
                ApiOrderCreateResultVo orderCreateResultVo = new ApiOrderCreateResultVo();
                orderCreateResultVo.setOrderNo(order.getOrderId());
                orderCreateResultVo.setPayType(order.getPayType().getCode());
                orderCreateResultVo.setTradeType(order.getOrderType().getCode());
                orderCreateResultVo.setSurplusAmount(order.getPayAmount());
                //--下面是各支付方式提供发起支付需要用到的东东
                PaymentBizParametersVo bizParameters = quickPaymentContext.getCurrent(order.getPayType()).getBizParameters(order);
                orderCreateResultVo.setBizParameters(bizParameters);
                return ApiResult.resultWith(AppCode.SUCCESS, orderCreateResultVo);
            } else {
                return ApiResult.resultWith(AppCode.ERROR, "创建订单失败");
            }
        } catch (Exception ex) {
            log.error("create发生异常：" + ex.getMessage(), ex);
            return ApiResult.resultWith(AppCode.ERROR, "创建订单失败");
        }
    }


    /**
     * 订单确认提交接口
     * @param userId
     * @param mobile
     * @param name
     * @param idCardNo
     * @param orderType
     * @return
     */
    @RequestMapping(value = "/checkout")
    @ResponseBody
    public ApiResult checkout(@RequestHeader(value = "userId") long userId,
                              @RequestParam(required = false, defaultValue = "") String mobile,
                              @RequestParam(required = false, defaultValue = "") String name,
                              @RequestParam(required = false, defaultValue = "") String idCardNo,
                              Integer orderType) {
        try {
            OrderEnum.OrderType tradeTypeEnum = EnumHelper.getEnumType(OrderEnum.OrderType.class, orderType);
            if (tradeTypeEnum == null) {
                return ApiResult.resultWith(AppCode.ERROR, "不支持的交易类型");
            }
            SubmitOrderInfo submitOrderInfo = new SubmitOrderInfo();
            submitOrderInfo.setUserId(userId);
            submitOrderInfo.setOrderType(tradeTypeEnum);
            submitOrderInfo.setName(name);
            submitOrderInfo.setMobile(mobile);
            submitOrderInfo.setIdCardNo(idCardNo);
            Order order= orderService.checkout(submitOrderInfo);

            return ApiResult.resultWith(AppCode.SUCCESS, order);

        } catch (Exception ex) {
            log.error("checkout发生异常：" + ex.getMessage(), ex);
            return ApiResult.resultWith(AppCode.ERROR, "确认订单报错");
        }
    }



}

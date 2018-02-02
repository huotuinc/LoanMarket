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
import com.huotu.loanmarket.common.utils.ApiResultException;
import com.huotu.loanmarket.common.utils.RegexUtils;
import com.huotu.loanmarket.service.entity.order.Order;
import com.huotu.loanmarket.service.enums.AppCode;
import com.huotu.loanmarket.service.enums.OrderEnum;
import com.huotu.loanmarket.service.enums.UserResultCode;
import com.huotu.loanmarket.service.model.order.ApiCheckoutResultVo;
import com.huotu.loanmarket.service.model.order.ApiOrderCreateResultVo;
import com.huotu.loanmarket.service.model.order.PayReturnVo;
import com.huotu.loanmarket.service.model.order.SubmitOrderInfo;
import com.huotu.loanmarket.service.model.payconfig.PaymentBizParametersVo;
import com.huotu.loanmarket.service.service.order.OrderService;
import com.huotu.loanmarket.service.service.thirdpay.QuickPaymentContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.interceptor.ExcludeDefaultInterceptors;
import java.text.MessageFormat;
import java.util.LinkedHashMap;

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
     *
     * @param userId      用户id
     * @param mobile      手机号码 选填
     * @param name        姓名，选填
     * @param idCardNo    身份证，选填
     * @param redirectUrl 回调地址，客户端自己定义
     * @param tradeType   订单交易类型
     * @param payType     支付方式
     * @return
     */
    @RequestMapping("/create")
    @ResponseBody
    public ApiResult create(@RequestHeader(value = Constant.APP_USER_ID_KEY, required = false, defaultValue = "0") Long userId,
                            @RequestParam(required = false, defaultValue = "") String mobile,
                            @RequestParam(required = false, defaultValue = "") String name,
                            @RequestParam(required = false, defaultValue = "") String idCardNo,
                            @RequestParam String redirectUrl,
                            Integer tradeType,
                            @RequestParam(required = false, defaultValue = "1") Integer payType

    ) {
        try {
            OrderEnum.OrderType tradeTypeEnum = EnumHelper.getEnumType(OrderEnum.OrderType.class, tradeType);

            ApiResult result = checkParam(tradeTypeEnum, mobile, idCardNo, name);
            if (result != null) {
                return result;
            }

            OrderEnum.PayType payTypeEnum = EnumHelper.getEnumType(OrderEnum.PayType.class, payType);
            if (payTypeEnum == null) {
                return ApiResult.resultWith(AppCode.ERROR, "不支持的支付类型");
            }

            SubmitOrderInfo submitOrderInfo = new SubmitOrderInfo();
            submitOrderInfo.setPayType(payTypeEnum);
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
     *
     * @param userId
     * @param mobile
     * @param name
     * @param idCardNo
     * @param tradeType
     * @return
     */
    @RequestMapping(value = "/checkout")
    @ResponseBody
    public ApiResult checkout(@RequestHeader(value = "userId") Long userId,
                              @RequestParam(required = false, defaultValue = "") String mobile,
                              @RequestParam(required = false, defaultValue = "") String name,
                              @RequestParam(required = false, defaultValue = "") String idCardNo,
                              Integer tradeType) {
        try {
            OrderEnum.OrderType tradeTypeEnum = EnumHelper.getEnumType(OrderEnum.OrderType.class, tradeType);

            ApiResult result = checkParam(tradeTypeEnum, mobile, idCardNo, name);
            if (result != null) {
                return result;
            }

            SubmitOrderInfo submitOrderInfo = new SubmitOrderInfo();
            submitOrderInfo.setUserId(userId);
            submitOrderInfo.setOrderType(tradeTypeEnum);
            submitOrderInfo.setName(name);
            submitOrderInfo.setMobile(mobile);
            submitOrderInfo.setIdCardNo(idCardNo);
            ApiCheckoutResultVo apiCheckoutResultVo = orderService.checkout(submitOrderInfo);

            return ApiResult.resultWith(AppCode.SUCCESS, apiCheckoutResultVo);

        } catch (Exception ex) {
            log.error("checkout发生异常：" + ex.getMessage(), ex);
            return ApiResult.resultWith(AppCode.ERROR, "确认订单报错");
        }
    }

    /**
     * 支付宝支付完成
     *
     * @param orderId
     * @return
     */
    @RequestMapping(value = "/return/order-{orderId}", method = RequestMethod.GET)
    @ExcludeDefaultInterceptors
    public String payReturn(@PathVariable(value = "orderId") String orderId, Model model) {
        PayReturnVo payReturnVo = orderService.getPayReturnInfo(orderId);
        if (payReturnVo == null) {
            throw new ApiResultException(ApiResult.resultWith(AppCode.ERROR, MessageFormat.format("订单:{0}不存在", orderId)));
        }
        model.addAttribute("returnInfo", payReturnVo);
        return "order/return";
    }

    /**
     * 检查参数
     *
     * @param tradeTypeEnum
     * @param mobile
     * @param idCardNo
     * @param name
     * @return
     */
    private ApiResult checkParam(OrderEnum.OrderType tradeTypeEnum, String mobile, String idCardNo, String name) {
        if (tradeTypeEnum == null) {
            return ApiResult.resultWith(AppCode.ERROR, "不支持的交易类型");
        }

        if (!tradeTypeEnum.equals(OrderEnum.OrderType.JINGDONG) &&
                !tradeTypeEnum.equals(OrderEnum.OrderType.TAOBAO)) {
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
        return null;
    }

    /**
     * 获取订单列表
     *
     * @param userId
     * @param pageIndex
     * @param pageSize
     * @return
     */
    @RequestMapping("/list")
    @ResponseBody
    public ApiResult list(@RequestHeader(value = Constant.APP_USER_ID_KEY, required = false, defaultValue = "0") Long userId,
                          @RequestParam(required = false, defaultValue = "1") int pageIndex,
                          @RequestParam(required = false, defaultValue = Constant.PAGE_SIZE_STR) int pageSize) {

        pageIndex = pageIndex <= 0 ? 1 : pageIndex;
        LinkedHashMap<String, Object> map = new LinkedHashMap<>();
        map.put("list", orderService.getList(userId, pageIndex, pageSize));
        return ApiResult.resultWith(AppCode.SUCCESS, map);
    }

}

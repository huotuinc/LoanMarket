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
import com.huotu.loanmarket.common.utils.ApiResult;
import com.huotu.loanmarket.common.utils.RegexUtils;
import com.huotu.loanmarket.service.entity.order.Order;
import com.huotu.loanmarket.service.enums.AppCode;
import com.huotu.loanmarket.service.enums.OrderEnum;
import com.huotu.loanmarket.service.enums.UserResultCode;
import com.huotu.loanmarket.service.service.order.OrderService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author guomw
 * @date 01/02/2018
 */
@Controller
@RequestMapping("/api/order")
public class OrderController {

    private static final Log log = LogFactory.getLog(OrderController.class);

    @Autowired
    private OrderService orderService;


    @RequestMapping("/create")
    private ApiResult create(@RequestHeader(value = Constant.APP_USER_ID_KEY, required = false, defaultValue = "0") Long userId,
                             @RequestParam(required = false, defaultValue = "") String mobile,
                             @RequestParam(required = false, defaultValue = "") String name,
                             @RequestParam(required = false, defaultValue = "") String idCardNo,
                             OrderEnum.OrderType orderType) {
        if (!orderType.equals(OrderEnum.OrderType.JINGDONG) && !orderType.equals(OrderEnum.OrderType.TAOBAO)) {
            if (StringUtils.isEmpty(mobile) || !RegexUtils.checkMobile(mobile)) {
                return ApiResult.resultWith(UserResultCode.CODE1);
            }
            if (StringUtils.isEmpty(idCardNo)) {
                return ApiResult.resultWith(UserResultCode.CODE1);
            }

            if (StringUtils.isEmpty(name)){
                return ApiResult.resultWith(UserResultCode.CODE1,"");
            }
        }

        Order order = orderService.create(userId, mobile, name, idCardNo, orderType);

        return ApiResult.resultWith(AppCode.SUCCESS, order);
    }
}

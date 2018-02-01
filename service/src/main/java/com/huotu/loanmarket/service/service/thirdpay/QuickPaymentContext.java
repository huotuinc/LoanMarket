/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼在地图中查看
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2016-2018. All rights reserved.
 */

package com.huotu.loanmarket.service.service.thirdpay;

import com.huotu.loanmarket.service.enums.OrderEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 第三方快捷支付接口提供入口
 * 想要获得各种支付方式的信息，从此入
 * @author wm
 */
@Service
public class QuickPaymentContext {
    final Map<Integer, QuickPaymentService> map = new ConcurrentHashMap<>();

    @Autowired
    public QuickPaymentContext(List<QuickPaymentService> quickPaymentServices) {
        quickPaymentServices.forEach(x -> map.put(x.getType().getCode(), x));
    }

    /**
     * 得到当前使用的支付方式
     * @param type
     * @return
     */
    public QuickPaymentService getCurrent(OrderEnum.PayType type){
        if(map.containsKey(type.getCode())){
            return map.get(type.getCode());
        }
        return null;
    }

    /**
     * 得到当前使用的支付方式
     * @param typeValue
     * @return
     */
    public QuickPaymentService getCurrent(int typeValue){
        if(map.containsKey(typeValue)){
            return map.get(typeValue);
        }
        return null;
    }
}
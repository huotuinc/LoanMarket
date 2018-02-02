/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼在地图中查看
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2016-2018. All rights reserved.
 */

package com.huotu.loanmarket.service.model.order;

import com.huotu.loanmarket.service.enums.OrderEnum;
import lombok.Data;

/**
 * @author guomw
 * @date 01/02/2018
 */
@Data
public class SubmitOrderInfo {

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 订单类型
     */
    private OrderEnum.OrderType orderType;
    /**
     * 姓名
     */
    private String name;
    /**
     * 手机号码
     */
    private String mobile;
    /**
     * 身份证号码
     */
    private String idCardNo;
    /**
     * 回调连接，客户端自己定义
     */
    private  String redirectUrl;

}

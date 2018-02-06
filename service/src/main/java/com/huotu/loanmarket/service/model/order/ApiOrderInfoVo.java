/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼在地图中查看
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2016-2018. All rights reserved.
 */

package com.huotu.loanmarket.service.model.order;

import lombok.Data;

/**
 * @author guomw
 * @date 02/02/2018
 */
@Data
public class ApiOrderInfoVo {

    private String orderId;

    private String orderName;

    private String desc;

    private String createTime;

    /**
     * 状态0已取消 1待支付 2认证中 3认证成功 4认证失败
     */
    private int status;

    /**
     * 状态描述
     */
    private String statusName;

    /**
     * 第三方认证地址
     */
    private String thirdAuthUrl;



}

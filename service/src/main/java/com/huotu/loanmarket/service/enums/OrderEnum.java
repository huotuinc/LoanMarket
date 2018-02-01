/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼在地图中查看
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2016-2018. All rights reserved.
 */

package com.huotu.loanmarket.service.enums;


import com.huotu.loanmarket.common.enums.ICommonEnum;
import lombok.AllArgsConstructor;

/**
 * @author guomw
 */

public interface OrderEnum {

    /**
     * 订单状态
     */
    @AllArgsConstructor
    enum OrderStatus implements ICommonEnum {

        CANCEL(0, "已取消"),
        Normal(1,"活动"),

        ;


        private int code;

        private String name;

        @Override
        public int getCode() {
            return code;
        }

        @Override
        public String getName() {
            return name;
        }
    }

    /**
     * 订单类型
     */
    @AllArgsConstructor
    enum OrderType implements ICommonEnum {
        /**
         * 支付宝
         */
        BACKLIST_BUS(0, "行业黑名单"),
        BACKLIST_FINANCE(1, "金融黑名单"),
        CARRIER(2, "运营商"),
        TAOBAO(3, "淘宝"),
        JINGDONG(4, "京东");


        private int code;

        private String name;

        @Override
        public int getCode() {
            return code;
        }

        @Override
        public String getName() {
            return name;
        }
    }

    /***
     * 支付类型
     */
    @AllArgsConstructor
    enum PayType implements ICommonEnum {
        /**
         * 支付宝
         */
        OTHER(0, "其他"),
        ALIPAY(1, "支付宝"),;


        private int code;

        private String name;

        @Override
        public int getCode() {
            return code;
        }

        @Override
        public String getName() {
            return name;
        }
    }

}

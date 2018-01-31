package com.huotu.loanmarket.service.enums;

import com.huotu.loanmarket.common.enums.ICommonEnum;
import com.huotu.loanmarket.common.enums.IMerchantParameterEnum;
import lombok.AllArgsConstructor;

/**
 * @Author hxh
 * @Date 2018/1/31 16:58
 */
public interface SesameEnum {
    /**
     * 风险信息行业编码
     */
    @AllArgsConstructor
    enum IndustryType implements IMerchantParameterEnum {
        FINANCIAL_CREDIT("AA", "金融信贷类"),
        PUBLIC_SECURITY("AB", "公检法"),
        PAYMENT_INDUSTRY("AC", "支付行业"),
        TRAVEL_INDUSTRY("AD", "出行行业"),
        HOTEL_INDUSTRY("AE", "酒店行业"),
        E_COMMERCE_INDUSTRY("AF", "电商行业"),
        RENTING_INDUSTRY("AG", "租房行业"),
        OPERATOR_INDUSTRY("AH", "运营商行业"),
        INSURANCE_INDUSTRY("AI", "报验行业"),
        LEASEHOLD_INDUSTRY("AK", "租赁行业");
        private String key;
        private String message;

        @Override
        public String getKey() {
            return key;
        }

        @Override
        public String getMessage() {
            return message;
        }
    }
}

package com.huotu.loanmarket.service.enums;

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

    /**
     * 风险类型编码
     */
    @AllArgsConstructor
    enum RankNumber implements IMerchantParameterEnum {
        OVERDUE_PAYMENT("AA001", "逾期未还款"),
        CASH("AA002", "套现"),
        EXECUTOR("AB001", "被执行人"),
        THIEVES("AC001", "盗卡者/盗卡者同伙"),
        FRAUDSTERS("AC002", "欺诈者/欺诈同伙"),
        EMBEZZLEMENT("AC003", "盗用操作/盗用者同伙"),
        EMBEZZLEMENT_CONFEDERATE("AC004", "盗用支出/盗用者同伙"),
        FRAUDULENT_CLAIM("AC005", "骗赔"),
        CASE_BASE_BLACKLIST("AC007", "案件库黑名单"),
        OVERDUE_PAYMENT_FOR_CAR_RENTAL("AD001", "汽车租赁逾期未付款"),
        CAR_RENTAL_OVERDUE("AD002", "汽车租赁逾期未还车"),
        NOT_PAID_FOR_OVERDUE_PAYMENT("AD003", "汽车租赁违章逾期未付款");

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

    /**
     * 风险代码
     */
    @AllArgsConstructor
    enum RankCode implements IMerchantParameterEnum {
        RANK_ONE("AA001001", "逾期1-30天"),
        RANK_TWO("AA001002", "逾期31-60天"),
        RANK_THREE("AA001003", "逾期61-90天"),
        RANK_FOUR("AA001004", "逾期91-120天"),
        RANK_FIVE("AA001005", "逾期121-150天"),
        RANK_SIX("AA001006", "逾期151-180天"),
        RANK_SEVEN("AA001007", "逾期180天以上"),
        RANK_EIGHT("AA0010010", "逾期1期"),
        RANK_NINE("AA001011", "逾期2期"),
        RANK_TEN("AA001012", "逾期3期"),
        RANK_ELEVEN("AA001013", "逾期4期"),
        RANK_TWELVE("AA001014", "逾期5期"),
        RANK_THIRTEEN("AA001015", "逾期6期"),
        RANK_FOURTEEN("AA001016", "逾期6期以上");

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

    /**
     * 风险等级
     */
    @AllArgsConstructor
    enum Level implements IMerchantParameterEnum {
        LOW("1", "低风险"),
        MIDDLE("2", "中风险"),
        HIGH("3", "高风险");

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

    /**
     * 异议状态
     */
    @AllArgsConstructor
    enum Status implements IMerchantParameterEnum {
        STATUS_ZERO("0", "原始状态"),
        STATUS_ONE("1", "用户有异议，信息核查中"),
        STATUS_TWO("2", "核查完毕，信息无误"),
        STATUS_THREE("3", "核查完毕，信息已修改"),
        STATUS_FIVE("5", "用户对此记录有异议，但异议主张经核查未获支持");

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

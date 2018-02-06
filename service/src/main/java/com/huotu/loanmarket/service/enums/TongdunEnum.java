package com.huotu.loanmarket.service.enums;

import com.huotu.loanmarket.common.enums.ICommonEnum;
import lombok.AllArgsConstructor;

/**
 * 同盾相关的枚举
 * @author wm
 */
public interface TongdunEnum {
    /**
     * 风险结果说明
     */
    @AllArgsConstructor
    enum DecisionType implements ICommonEnum {
        ACCEPT(0,"建议通过"),
        REVIEW(1,"建议审核"),
        REJECT(2,"建议拒绝");
        private int code;
        private String message;
        @Override
        public int getCode() {
            return code;
        }
        @Override
        public String getName() {
            return message;
        }
    }

    /**
     * 搜索类型
     */
    @AllArgsConstructor
    enum LogSearchType implements ICommonEnum {
        REPORT_ID(0,"报告编号"),
        NAME(1,"姓名"),
        ID_NUMBER(2,"身份证"),
        MOBILE(3,"手机号"),
        ORDER_ID(4,"支付订单号");
        private int code;
        private String message;
        @Override
        public int getCode() {
            return code;
        }
        @Override
        public String getName() {
            return message;
        }
    }

    @AllArgsConstructor
    enum ReportStatus implements ICommonEnum{
        FAIL(0,"生成失败"),
        SUCCESS(1,"生成成功"),
        BUILDING(2,"生成中");

        private int code;
        private String message;
        @Override
        public int getCode() {
            return code;
        }
        @Override
        public String getName() {
            return message;
        }
    }

    /**
     * 联系人社会关系
     */
    @AllArgsConstructor
    enum ContactRelation implements ICommonEnum{
        /**
         * 父亲
         */
        FATHER(0,"father"),
        /**
         * 母亲
         */
        MOTHER(1,"mother"),
        /**
         * 配偶
         */
        SPOUSE(2,"spouse"),
        /**
         * 子女
         */
        CHILD(2,"child"),
        /**
         * 其他亲属
         */
        OTHER_RELATIVE(2,"other_relative"),
        /**
         * 朋友
         */
        FRIEND(2,"friend"),
        /**
         * 同事
         */
        COWORKER(2,"coworker"),
        /**
         * 其他
         */
        OTHERS(3,"others");

        private int code;
        private String message;
        @Override
        public int getCode() {
            return code;
        }
        @Override
        public String getName() {
            return message;
        }
    }
}
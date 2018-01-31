package com.huotu.loanmarket.service.enums;

import com.huotu.loanmarket.common.enums.IMerchantParameterEnum;
import com.huotu.loanmarket.service.config.MerchantConfigType;
import lombok.AllArgsConstructor;

/**
 * 系统参数列表（key对应表cl_merchant_cfg_items（页面）的item_code（参数编号））
 *
 * @author hxh
 * @date 2017-12-04
 */
public interface ConfigParameter {
    /**
     * 芝麻信用参数
     */
    @AllArgsConstructor
    @MerchantConfigType(type = MerchantConfigEnum.SESAME)
    enum SesameParameter implements IMerchantParameterEnum {
        API_ID("zhima_api_id", "API账号"),
        API_SECRET("zhima_api_secret", "API密钥"),
        NAME_AND_IDCARDNUM_URL("zhima_name_and_idcardnum_url", "身份证号和姓名比对接口"),
        APP_ID("zhima_app_id","应用标识"),
        PUBLIC_KEY("zhima_public_key","芝麻公钥"),
        PRIVATE_KEY("zhima_private_key","私钥");
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
     * 运营商接口参数
     */
    @AllArgsConstructor
    @MerchantConfigType(type = MerchantConfigEnum.CARRIER)
    enum CarrierParameter implements IMerchantParameterEnum {
        PARTNER_CODE("carrier_partner_code", "合作方标识"),
        PARTNER_KEY("carrier_partner_key", "合作方密钥");

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
     * 短信接口参数
     */
    @AllArgsConstructor
    @MerchantConfigType(type = MerchantConfigEnum.MESSAGE)
    enum MessageParameter implements IMerchantParameterEnum {
        URL("message_url", "短信发送地址"),
        ACCOUNT("message_account", "账号"),
        CHANNEL_NO("message_channelNo", "渠道编号"),
        PASSAGEWAY("message_passageway", "短信通道"),
        PASSWORD("message_password", "密码");
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

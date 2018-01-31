package com.huotu.loanmarket.service.enums;

import com.huotu.loanmarket.common.enums.IMerchantParameterEnum;
import com.huotu.loanmarket.service.config.MerchantConfigType;
import com.huotu.loanmarket.service.enums.MerchantConfigEnum;
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
        API_ID("api_id", "API账号"),
        API_SECRET("api_secret", "API密钥"),
        NAME_AND_IDCARDNUM_URL("name_and_idcardnum_url", "身份证号和姓名比对接口");
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

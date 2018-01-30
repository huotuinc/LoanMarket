package com.huotu.loanmarket.service.entity.merchant;

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
        APP_ID("zhima_app_id", "芝麻应用标识"),
        PRIVATE_KEY("zhima_private_key", "商家私钥"),
        PUBLIC_KEY("zhima_public_key", "芝麻公钥");
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

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
        APP_ID("zhima_app_id","行业黑名单应用标识"),
        PUBLIC_KEY("zhima_public_key","行业黑名单芝麻公钥"),
        PRIVATE_KEY("zhima_private_key","行业黑名单私钥"),
        CHEAT_APP_ID("zhima_cheat_app_id","欺诈信息应用标识"),
        CHEAT_PUBLIC_KEY("zhima_cheat_public_key","欺诈信息公钥"),
        CHEAT_PRIVATE_KEY("zhima_cheat_private_key","欺诈信息私钥");
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

    /**
     * 认证费配置参数
     */
    @AllArgsConstructor
    @MerchantConfigType(type = MerchantConfigEnum.AUTH_FEE)
    enum AuthFeeParameter implements IMerchantParameterEnum {
        /**
         * 行业黑名单费用
         */
        BACKLIST_BUS_FEE("auth_backlist_bus_fee", "行业黑名单认证费"),
        BACKLIST_FINANCE_FEE("auth_backlist_finance_fee", "金融黑名单认证费"),
        CARRIER_FEE("auth_carrier_fee", "运营商认证费"),
        TAOBAO_FEE("auth_taobao_fee", "淘宝授权认证"),
        JINGDONG_FEE("auth_jingdong_fee", "京东授权认证费");
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
     * 支付宝移动网站支付配置参数
     */
    @AllArgsConstructor
    @MerchantConfigType(type = MerchantConfigEnum.ALIPAY)
    enum AlipayParameter implements IMerchantParameterEnum {
        ALIPAY_APPID("alipay_app_id", "支付宝应用id"),
        ALIPAY_PUBLIC_KEY("alipay_public_key", "支付宝公钥，验签等使用"),
        ALIPAY_RSA_PUBLIC_KEY("alipay_rsa_public_key", "暂无用，用于生成alipayPublicKey备份"),
        ALIPAY_RSA_PRIVAT_EKEY("alipay_rsa_private_key", " 支付宝私钥");
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
     * 同盾接口参数
     */
    @AllArgsConstructor
    @MerchantConfigType(type = MerchantConfigEnum.TONGDUN)
    enum TongdunParameter implements IMerchantParameterEnum {
        SUBMIT_URL("tongdun_submiturl", "申请接口地址"),
        QUERY_URL("tongdun_queryurl", "查询报告接口地址"),
        PARTNER_CODE("tongdun_partnercode", "合作方标识"),
        PARTNER_KEY("tongdun_partnerkey ", "合作方密钥"),
        PARTNER_APP("tongdun_partneraapp ", "应用名");

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
     * 同盾审核规则id
     */
    @AllArgsConstructor
    @MerchantConfigType(type = MerchantConfigEnum.TONGDUN_RULE)
    enum TongdunRuleIdParameter implements IMerchantParameterEnum {
        COURT_LOSEFAITH("tdrule_court_losefaith", "法院失信的规则id"),
        COURT_EXECUTION("tdrule_court_Execution", "法院执行的规则id"),
        COURT_CASERULEIDS("tdrule_court_caseruleids", "法院结案的规则id"),
        DISCREDIT("tdrule_discredit", "信贷逾期的规则id"),
        FUZZYNAMEHITS("tdrule_fuzzynamehits", "模糊名单命中的规则id"),
        CRIMINALWANTED("tdrule_criminalwanted", "犯罪通缉的规则id"),
        PHONEN_UMBER_RISKS("tdrule_phonen_umber_risks", "手机号风险的规则id"),
        PLATFORM_APPLY7("tdrule_platform_apply7", "7天小贷申请的规则id"),
        PLATFORM_APPLY30("tdrule_platform_apply30", "30天小贷申请的规则id"),
        PLATFORM_APPLY90("tdrule_platform_apply90", "3个月小贷申请的规则id");
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
     * 通用
     */
    @AllArgsConstructor
    @MerchantConfigType(type = MerchantConfigEnum.GENERAL)
    enum GeneralParameter implements IMerchantParameterEnum {
        YINGYONGBAO("general_yingyongbao", "应用宝地址"),
        ORDER_RECYCLE("order_recycle","未支付订单时效,单位分钟"),
        YOU_XIN_API_URL("you_xin_api_url","有信接口地址"),
        FACE_ERROR_CONFIG("face_error_config","人脸识别错误值"),
        ;
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

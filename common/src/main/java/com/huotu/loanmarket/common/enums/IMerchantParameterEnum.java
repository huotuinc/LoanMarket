package com.huotu.loanmarket.common.enums;

/**
 * 系统配置使用（key对应表cl_merchant_cfg_items（页面）的item_code（参数编号））
 *
 * @author hxh
 * @date 2017-12-04
 */
public interface IMerchantParameterEnum {
    /**
     * 参数编号
     *
     * @return
     */
    String getKey();

    /**
     * 参数说明
     *
     * @return
     */
    String getMessage();
}

package com.huotu.loanmarket.service.service.merchant;

import java.util.Map;

/**
 * @Author hxh
 * @Date 2018/1/30 17:19
 */
public interface MerchantCfgService {
    /**
     * 根据第三方获取参数
     *
     * @param merchantId
     * @return
     */
    Map<String, String> getConfigItem(Integer merchantId);
}

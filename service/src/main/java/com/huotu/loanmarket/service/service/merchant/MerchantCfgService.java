package com.huotu.loanmarket.service.service.merchant;

import com.huotu.loanmarket.common.utils.ApiResult;
import com.huotu.loanmarket.service.entity.merchant.MerchantConfigItem;
import com.huotu.loanmarket.service.enums.MerchantConfigEnum;

import java.util.List;
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

    /**
     * 根据第三方获取参数
     * @param merchantId
     * @param configEnum
     * @return
     */
    Map<String, String> getConfigItem(Integer merchantId, MerchantConfigEnum configEnum);
    /**
     * 查询
     *
     * @param type
     * @return
     */
    List<MerchantConfigItem> findByType(Integer type);

    /**
     * 保存第三方配置参数
     *
     * @param merchantConfigItem
     * @return
     */
    ApiResult save(MerchantConfigItem merchantConfigItem);

    /**
     * 根据编号查询配置参数
     *
     * @param configId
     * @param merchantId
     * @return
     */
    MerchantConfigItem findByConfigIdAndMerchantId(Long configId, Integer merchantId);
    /**
     * 禁用（删除）
     *
     * @param configId
     */
    void delete(Long configId);
}

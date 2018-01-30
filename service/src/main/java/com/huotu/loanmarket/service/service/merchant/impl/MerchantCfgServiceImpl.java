package com.huotu.loanmarket.service.service.merchant.impl;

import com.huotu.loanmarket.service.entity.merchant.MerchantConfigItem;
import com.huotu.loanmarket.service.repository.merchant.MerchantConfigItemRepository;
import com.huotu.loanmarket.service.service.merchant.MerchantCfgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author hxh
 * @Date 2018/1/30 17:20
 */
@Service
public class MerchantCfgServiceImpl implements MerchantCfgService{
    @Autowired
    private MerchantConfigItemRepository merchantConfigItemRepository;
    @Override
    public Map<String, String> getConfigItem(Integer merchantId) {
        List<MerchantConfigItem> configItemList = merchantConfigItemRepository.findByMerchantId(merchantId);
        Map<String, String> map = new HashMap<>(configItemList.size());
        configItemList.forEach(p -> {
            map.put(p.getCode(), p.getValue());
        });
        return map;
    }
}

package com.huotu.loanmarket.service.service.merchant.impl;

import com.huotu.loanmarket.common.Constant;
import com.huotu.loanmarket.common.enums.EnumHelper;
import com.huotu.loanmarket.common.enums.IMerchantParameterEnum;
import com.huotu.loanmarket.common.utils.ApiResult;
import com.huotu.loanmarket.service.config.MerchantConfigType;
import com.huotu.loanmarket.service.entity.merchant.MerchantConfigItem;
import com.huotu.loanmarket.service.enums.AppCode;
import com.huotu.loanmarket.service.enums.ConfigParameter;
import com.huotu.loanmarket.service.enums.MerchantConfigEnum;
import com.huotu.loanmarket.service.repository.merchant.MerchantConfigItemRepository;
import com.huotu.loanmarket.service.service.merchant.MerchantCfgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author hxh
 * @Date 2018/1/30 17:20
 */
@Service
public class MerchantCfgServiceImpl implements MerchantCfgService {
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

    @Override
    public Map<String, String> getConfigItem(Integer merchantId, MerchantConfigEnum configEnum) {
        List<MerchantConfigItem> configItemList = merchantConfigItemRepository.findByMerchantIdAndType(merchantId, configEnum);
        Map<String, String> map = new HashMap<>(configItemList.size());
        configItemList.forEach(p -> {
            map.put(p.getCode(), p.getValue());
        });
        return map;
    }

    @Override
    public List<MerchantConfigItem> findByType(Integer type) {
        List<MerchantConfigItem> items = new ArrayList<>();
        MerchantConfigItem item;
        MerchantConfigEnum enumType = EnumHelper.getEnumType(MerchantConfigEnum.class, type);
        Class<?>[] declaredClasses = ConfigParameter.class.getDeclaredClasses();
        for (Class<?> declaredClass : declaredClasses) {
            if (declaredClass.isEnum()) {
                MerchantConfigType fitConfigType = declaredClass.getAnnotation(MerchantConfigType.class);
                if (fitConfigType != null && (fitConfigType.type().equals(enumType))) {
                    Class<Enum> clazz = (Class<Enum>) declaredClass;
                    Enum[] arrEnums = clazz.getEnumConstants();
                    for (Enum obj : arrEnums) {
                        IMerchantParameterEnum data = (IMerchantParameterEnum) obj;
                        item = setItems(type, data.getKey(), data.getMessage(), Constant.MERCHANT_ID);
                        items.add(item);
                    }
                }
            }
        }
        return items;
    }

    @Override
    public ApiResult save(MerchantConfigItem merchantConfigItem) {
        if (merchantConfigItem.getConfigId() != null && merchantConfigItem.getConfigId() > 0) {
            //编辑
            MerchantConfigItem item = merchantConfigItemRepository.findByConfigIdAndMerchantId(merchantConfigItem.getConfigId(), merchantConfigItem.getMerchantId());
            merchantConfigItem.setCreateTime(item.getCreateTime());
        } else {
            //新增
            MerchantConfigItem configItem = merchantConfigItemRepository.findByCodeAndMerchantId(merchantConfigItem.getCode(), merchantConfigItem.getMerchantId());
            if (configItem != null) {
                return ApiResult.resultWith(AppCode.SYSTEM_BAD_REQUEST.getCode(), "系统参数中参数编号已有");
            }
            merchantConfigItem.setCreateTime(LocalDateTime.now());
        }
        merchantConfigItemRepository.save(merchantConfigItem);
        return ApiResult.resultWith(AppCode.SUCCESS);
    }

    @Override
    public MerchantConfigItem findByConfigIdAndMerchantId(Long configId, Integer merchantId) {
        return merchantConfigItemRepository.findByConfigIdAndMerchantId(configId, merchantId);
    }

    @Override
    public void delete(Long configId) {
        merchantConfigItemRepository.delete(configId);
    }

    private MerchantConfigItem setItems(Integer type, String code, String name, Integer merchantId) {
        MerchantConfigItem item = merchantConfigItemRepository.findByCodeAndMerchantId(code, merchantId);
        if (item == null) {
            item = new MerchantConfigItem();
            item.setCode(code);
            item.setMerchantId(merchantId);
            item.setName(name);
            MerchantConfigEnum enumType = EnumHelper.getEnumType(MerchantConfigEnum.class, type);
            item.setType(enumType);
        }
        return item;
    }
}

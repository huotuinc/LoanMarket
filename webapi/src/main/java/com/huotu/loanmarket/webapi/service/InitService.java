/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼在地图中查看
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2016-2018. All rights reserved.
 */

package com.huotu.loanmarket.webapi.service;

import com.huotu.loanmarket.common.Constant;
import com.huotu.loanmarket.common.enums.IMerchantParameterEnum;
import com.huotu.loanmarket.service.config.MerchantConfigType;
import com.huotu.loanmarket.service.entity.merchant.MerchantConfigItem;
import com.huotu.loanmarket.service.entity.system.SmsTemple;
import com.huotu.loanmarket.service.enums.ConfigParameter;
import com.huotu.loanmarket.service.enums.MerchantConfigEnum;
import com.huotu.loanmarket.service.enums.SmsTemplateSceneTypeEnum;
import com.huotu.loanmarket.service.repository.merchant.MerchantConfigItemRepository;
import com.huotu.loanmarket.service.repository.system.SmsTemplateRepository;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author helloztt
 */
@Service
public class InitService {

    private static final Log log = LogFactory.getLog(InitService.class);
    @Autowired
    private SmsTemplateRepository smsTemplateRepository;
    @Autowired
    private MerchantConfigItemRepository merchantConfigItemRepository;
    @Autowired
    private List<MerchantConfigItem> merchantConfigItems = new ArrayList<>();


    @PostConstruct
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public void init() {

        initTemp();
        initSmsMessage();
        initsesame();
    }


    @Transactional(rollbackFor = RuntimeException.class)
    public void initTemp() {
        if (smsTemplateRepository.count() > 0) {
            return;
        }
        SmsTemplateSceneTypeEnum[] smsTemplateEnums = SmsTemplateSceneTypeEnum.values();
        List<SmsTemple> smsTempleList = new ArrayList<>();
        LocalDateTime localDateTime = LocalDateTime.now();
        for (SmsTemplateSceneTypeEnum smsTempEnum : smsTemplateEnums) {
            SmsTemple smsTemple = new SmsTemple();
            smsTemple.setMerchantId(Constant.MERCHANT_ID);
            smsTemple.setSceneType(smsTempEnum.getCode());
            smsTemple.setTmplContent(smsTempEnum.getTemplate());
            smsTemple.setCreatetime(localDateTime);
            smsTempleList.add(smsTemple);
        }
        smsTemplateRepository.save(smsTempleList);
        smsTemplateRepository.flush();
    }


    /**
     * 初始化芝麻认证参数
     */
    @Transactional(rollbackFor = RuntimeException.class)
    public void initsesame() {
        Map<String, String> map = new HashMap<>();
        map.put("zhima_app_id", "300001534");
        map.put("zhima_private_key", "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAJAyFvR1aHAvMzrZLwdU2o4ypVto5iEg0XU+i7GcH+ifz0VCFfYzlgm0SzKdVTMQZTAu/yu3vU0zq6XYhDw8zDFfOydh6jwg4Z5Op0yp/GOCYUeQ8RXh/CLeqWZ6U445JF1aLRq9WWSJMrHd/SUS/H6ZCd395FM1/ALRv2MgwasdAgMBAAECgYB1e5ogSphw7sP6qoEapBP9z13SgEEftLFzqSX/64hD7BDgCNwD4DvaZD7wWifUlwMaou/cGrPtdq/fS+1RdueWBxBXek7E2Mr8XoXdg1hYU/cwAwW/d+yDNzIQAi5YToXLdWoRAFIPLXItR0eqgVas2myBIBghrHnCn/RpTzkasQJBANg7jC0GUfCCibZPns5ztplzIhcf5o+J+ZTQafLvlb9gnKWfk/aFA85tmGP/0z9lqP+CvhyoB0fuObuCS8WaDOMCQQCqtvgSA8+cb+6PFpc0AoLtzbyQg8q1hE6qsAveov65LKnF1/Y5g+5H4/FoZjY5rzLMA/29tnyoLIMZnlWH+uf/AkEAyEzA2KRWGtiRAJRYox2FNDf2iD1Asg45Z6R3wFY2/QRdlv5vbZhhokLbOyarZLqjBazytlDgDb2bNRrJXc4JyQJAbu2Pil0xlWxiLlY2kBQlsZJjOeB5YrODToQwYk21iOvNsYIgO8VPmyUxo23vRFJkho79XT3sCHdMBOSOnVpbTwJAKcLR+l4/vUYOCL/tuIDco0NT0PpdDnX5cwWFQmemk3LxXOnsV6wYJd849eK7d7M+hpibHMTnWUQw8Vm3F6N4WQ==");
        map.put("zhima_public_key", "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCAyIKQG7rZ5k4XSHtf9XrykSR8kj24E534fjd67hyGiGX4xgqw4X7pEyPwuaADbimA6fM3bk2Sl5+Z9VhgCZMfyPYbR/oI/DP3d9/1lIJm4p8t97kiJ2732V//IKzwxcWetrwiw6p4tkEf4KWkJRHskmRvnCOPFTbSf0EFLNLrDQIDAQAB");

        initMerchantConfigItem(map, MerchantConfigEnum.SESAME);
    }


    /**
     * 初始化短信接口参数数据
     *
     * @author guomw
     */
    @Transactional(rollbackFor = RuntimeException.class)
    public void initSmsMessage() {
        Map<String, String> map = new HashMap<>();
        map.put("message_url", "http://smssh1.253.com/msg/send/json");
        map.put("message_account", "N8310514");
        map.put("message_password", "Htlm888888");
        map.put("message_channelNo", "");
        map.put("message_passageway", "");
        initMerchantConfigItem(map, MerchantConfigEnum.MESSAGE);
    }


    /***
     * 初始化指定第三方参数配置
     * @param mapData
     * @param type
     * @author guomw
     */
    @Transactional(rollbackFor = RuntimeException.class)
    public void initMerchantConfigItem(Map<String, String> mapData, MerchantConfigEnum type) {
        try {
            List<MerchantConfigItem> itemList = merchantConfigItems.stream().filter(t -> type.equals(t.getType())).collect(Collectors.toList());
            List<MerchantConfigItem> items = new ArrayList<>();
            Class<?>[] declaredClasses = ConfigParameter.class.getDeclaredClasses();
            for (Class<?> declaredClass : declaredClasses) {
                if (declaredClass.isEnum()) {
                    MerchantConfigType fitConfigType = declaredClass.getAnnotation(MerchantConfigType.class);
                    if (fitConfigType != null && (type.equals(fitConfigType.type()))) {
                        Class<Enum> clazz = (Class<Enum>) declaredClass;
                        Enum[] arrEnums = clazz.getEnumConstants();
                        for (Enum obj : arrEnums) {
                            IMerchantParameterEnum parameter = (IMerchantParameterEnum) obj;
                            if (itemList.stream().noneMatch(t -> parameter.getKey().equals(t.getCode()))) {
                                MerchantConfigItem item = new MerchantConfigItem();
                                item.setCode(parameter.getKey());
                                item.setMerchantId(Constant.MERCHANT_ID);
                                item.setName(parameter.getMessage());
                                item.setValue(mapData.get(parameter.getKey()));
                                item.setType(type);
                                items.add(item);
                            }
                        }
                    }
                }
            }

            if (items.size() > 0) {
                merchantConfigItemRepository.save(items);
            }
        } catch (Exception ex) {
            log.error(ex);
        }
    }
}

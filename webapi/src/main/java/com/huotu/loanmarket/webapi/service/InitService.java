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

    private List<MerchantConfigItem> merchantConfigItems = new ArrayList<>();


    @PostConstruct
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public void init() {
        initConfigs();
        initAlipay();
        initTemp();
        initSmsMessage();
        initsesame();
        initAuthFee();
    }

    @Transactional(rollbackFor = RuntimeException.class)
    public void initConfigs() {
        merchantConfigItems = merchantConfigItemRepository.findByMerchantId(Constant.MERCHANT_ID);
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
     * 支付宝参数
     */
    @Transactional(rollbackFor = RuntimeException.class)
    public void initAlipay() {
        Map<String, String> map = new HashMap<>();
        map.put(ConfigParameter.AlipayParameter.ALIPAY_APPID.getKey(), "2017120900477560");
        map.put(ConfigParameter.AlipayParameter.ALIPAY_PUBLIC_KEY.getKey(), "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAqexBEoP9mPAA3HH8uvrBbW4DfRqvEM1AnO/zfR1B6ij9MWnSI7XnAq/nizNAn6XWA87ouEx1uY+wSdOyOMbQjLIcVxHP9H6ktZ/up4oagE8O6uTnQLR0zgz0nFi54HicrEQ+ufhYbNpK1O8glL5ljRLP6AbZbIjYjvbAFHknjwBV8o699KgLF0k6QduE0/+5Eocr4YiNopeq+1GpIZ8Yg6mWuIXMgq2clv7cKHpayCa2BKnvumSQysxP932s5T/LkMbF4Y3wnYmUEffWpTpFjPDZHsHGtDc81pcTT7m02ZkbaO1Y0nCympWwV5RKFeEOiND3ZkJlo1z0wk7IpuvJIQIDAQAB");
        map.put(ConfigParameter.AlipayParameter.ALIPAY_RSA_PUBLIC_KEY.getKey(), "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAnKE7ApA2jS6oMZwu+wHwrY0cmH1wm+8TRcE8h/cvz+uV8B3QZ/4srZ1IpZ44CnQ7NW7frmE2cLf99A+164c17snNBaq30OhISdwQA9oZ70TxdkkPjHe9X1YpvKn+eVc+hyPMcu8Y7SKQoewtzETb4LJnhSx13UFo/Wjs5fIFh1GS9VFmpqbTr9E/RPgZUWVTiTISFM00uTcXfHLY9YfYx8fJi8mA2wZXGNr/s9+2V6ArxCtK/rVWOQ/qY/M2waXSQ8qXx24qpp7iOwbVbCINuUdx7ji9KCGnY9/T/bpfNexeQVkl8BKUdRghToTUFX1JGrc2ql5uPlTJnuAtS/sNFwIDAQAB");
        map.put(ConfigParameter.AlipayParameter.ALIPAY_RSA_PRIVAT_EKEY.getKey(), "MIIEvwIBADANBgkqhkiG9w0BAQEFAASCBKkwggSlAgEAAoIBAQCcoTsCkDaNLqgxnC77AfCtjRyYfXCb7xNFwTyH9y/P65XwHdBn/iytnUilnjgKdDs1bt+uYTZwt/30D7XrhzXuyc0FqrfQ6EhJ3BAD2hnvRPF2SQ+Md71fVim8qf55Vz6HI8xy7xjtIpCh7C3MRNvgsmeFLHXdQWj9aOzl8gWHUZL1UWamptOv0T9E+BlRZVOJMhIUzTS5Nxd8ctj1h9jHx8mLyYDbBlcY2v+z37ZXoCvEK0r+tVY5D+pj8zbBpdJDypfHbiqmnuI7BtVsIg25R3HuOL0oIadj39P9ul817F5BWSXwEpR1GCFOhNQVfUkatzaqXm4+VMme4C1L+w0XAgMBAAECggEBAIYgR0ENr7k8oAlDnrOR2ME1mqw2lwzhMNbGAAjKsyXQrugdD89NyIKvsnsovwKsv9sk4+UXGCB0XsL2BSn48kVWh/v3UktR7j1ZdllmFllf54oMTIVUMFWImlsWE3VjMYdWL4iomObQd5xBfIJ04PlMrPtMtG3QypBenqbWlsdlyVNXhQ2mmjPDFUemzbY0upS9Ag6syQR+CV0LG1DFi7Lavs4sPrNeSY6YG9Div/YwQpdjD/0ogRa8/fpreAc+fXF+AosfGc1xyS6DPXs+azdhbt+zwpWfdloqNGBGHFpF9wv4GK6ZYStf8JREV120QLG4PiHX7iJl8c2j7Dc0OoECgYEA4EhsKXaMk7qx/Bi2nvIKhKesCsFb4E/G0s43od5wiqBIKLFUfdQ2fI6sDmQ5dUWklDq88fn4WZQkrPo6oKEMmqrmUrGlw3Dw4/NljshxwNkFDLZoXw6T49CkvMvDenkr5TjyjMgSVtybvFrSCTMGSABYYn1ejq7iAOXBRuwE2NcCgYEAsseaq1AGp2viy0OXp1FOwLu/GXD/cml9DOtosm5dGYoO9FlHFiQwQh60g0EMwN9B0FR8iFItg+OgYGz8Lq0/ZrfmMKvkYS+YrcMa8mA236sXXWrINKNQIyH0j8mLEsVc1mPnv0GCS/FiylrRUyXQS5bL9eYqyLpp2NdPeODXpcECgYEA1TckVFqWE871a2KXIg2ZwDiiTms5i8prinZu3txXgwIS91R1rVV0Iv+DQ/81vG3jouMgQq3P0mEKaMNNvuy7zcHQSicGHgGdkCcQkieuNJZbvZUfLdbu8zGjmrPfduwAtd5ofzuoa5k4GF0CtaojO4nQfHkQznYk9pN1JZY0mWsCgYEAlsynOjmWY0ItWyb4lLmQpCG5X8xgrDEuuecXuOo+vXwmgRWNUK/VxPABc3U7PLUU3pVlNdebbNRkQvGrBfQ8tngIQqW7Tf7H2knoQOnHX4MBglq/5SacQgYyM9bc6EhxtqUupbSsI0LJAcb5KhmLSTc5c4FiNU6IWc/DyUJepAECgYB6l4ma5EhoRk5hTFPFCe1SJIrU2aO6nrHbNTLQl1wUkLUYGuDTxbhbrL0HC6xMSyl1LaMQTzMFlg0/1aMwoYbtXlpULnU/hCO+ORtelJ9V4yZUNR7lGucOk+OiBNz/aa14QA7eVqf0m8CPfVCeHiTcijyDs3QyngXCmD2TeTgkQA==");
        initMerchantConfigItem(map, MerchantConfigEnum.ALIPAY);
    }

    /**
     * 初始化芝麻认证参数
     */
    @Transactional(rollbackFor = RuntimeException.class)
    public void initsesame() {
        Map<String, String> map = new HashMap<>();
        map.put("zhima_api_id", "6eadbeb657ab4128b2c0298c49f07157");
        map.put("zhima_api_secret", "8f385900e02440228734ef6ce471c61c");
        map.put("zhima_name_and_idcardnum_url", "https://cloudapi.linkface.cn/data/verify_id_name");
        map.put("zhima_app_id", "300001906");
        map.put("zhima_public_key", "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCQl/xB4ZcyV5Yn4Lz1DetwIeh/V3aP1Y30XRWE9VZJO6sWMjHF03RllZC9HQX54jsQ24lrk2zdytSDuEbkyYB8r/HRtRmF2qzCFfFGhvLGWAXIL/s7YxKtDw12zJtdSpgI0/+UKjKYlH3KjOdv0tr1NQbqA6xLTM8+8w9I8tWc/QIDAQAB");
        map.put("zhima_private_key", "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBALUEqZqAL/q/zi2Jja6qgn7vV1DHBOv7HDkuIHfdyeT0QUZM4ZcWIUYFqCs7g17M7GsH4BEegJMb0wMyh1lWuOXV3xv7Y/ACiQjVVxbrBb8+CdoVQUbcqOv3RkOfULhWh2U8+YmkKwpweQ4qt2pJqAihiReLdDNYRPqsDLcm2pSVAgMBAAECgYBwkYSzOrIbmfmqYdcE3q4mJ8rBxfHumgr0GBZC1F7ADZEmGZtULIPmmo/4ypqocoE3Ef+qRbCpSu1PGLYIvaBe6Ws3elSNbTqtQpMmHECtHRjM36tM6I1c8FlFQ7MzQ8OOWaa7GcDRsixfvtDmFc7TCjF1WQIEIjUSBhjoVIwQgQJBAN0EPKYbSch1sJv8FUjVJ0alMYCt25VCvoehAw8HxyzXIF1PES11VbSYkv9Khrc6KmImC8+AiDG0EFBw+uwNVjUCQQDRq6lo61vjLXDgiigmGFa0I20MWWZrAcP/aeuQObjrSJl26oe8tFCiCNvVXzPLSHT1QatODkg5Dq5TORn4lJDhAkEA1Eg0hIrjRGId/sSSIQFylv0l3eWfae7Ql+7DxFUBLoV2NYkzA4mWhz6gugn3+NcqTFkksOpQZgUAQnMx3zY0HQJATvr5+tLFr0LbxUllrEv4xSyfFErM4Lwuh5TvrkmAFekoHfpknTnTMeZiYPLWwNmPbR+KlwGcRRn5otHcrZ3RIQJBAIZ9UaQKsdV30wxnWI576Ewg6uzbQVjLXmFh9Z+wU1j9WofBQA2aKgQtmfNd1lIOLOzuJ6mu3ftGr09Fak7Epds=");
        map.put("zhima_cheat_app_id", "1004254");
        map.put("zhima_cheat_public_key", "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCeSC/h+rb55IuG+tPWjMr1+3y4bNBImSb1aG3NogUAYBdMP7i9LHGSkW/jpzzdEOvG1U2RUiYJeanlNh6dkIlixDuZ2NSq4zDLBQnlFHKpa1hb32bD5zG2YtDWcLamKfKqtpzofDD6kc/vbzxMCm/P4Ehq98MPqoq1lMc6MFniGwIDAQAB");
        map.put("zhima_cheat_private_key", "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAKaFv9uRLnWuyF7G\n" +
                "+s7ZaTcwHjs8DK0xwWjnTxRra8ueEJExD8tjEw7Vj5B0PiZGelsUkUrh0u+BtFa/\n" +
                "hk2GUtouqzoaEH8q4Aukk8HHA2pZiOUiB2m1hs4s1NS7mmJlO+lgQfFGCoUksW8U\n" +
                "+dn28h+jTf3HC5Ptx4W/0TUYNZ11AgMBAAECgYBUDNhdA/eWUMU0CW0YsOYJhBEO\n" +
                "Ru4DEUUVJQNmRB4hpUOpdUEKoJRC/Yo8GGAyQ4qIpGX9jORCHY6f5oXsZWs1grjA\n" +
                "HznpVX0B4vx0sMe7ZNrhcCP11r1rmmGsvJDdiCTpfk+QEhKv8kxXyB13vmHg1L4a\n" +
                "dUYI7sCggk0AuH7YIQJBANIbmtnxI2kRgt/vRdhBh7jfR1ee5kvqEEt9HFMEs+mK\n" +
                "9ItmHqSN9O8XeNPCxs5hKBmhXXWMmxmhrrZz+I9i0m0CQQDK5QYQXVQRr8TE3z3R\n" +
                "NNb5zH52e6ECTTxeaUl9mWs1IYIXMoczfFDFwx4ZfRlN7u7Np5Ypa1wRc8Uq7Kv9\n" +
                "B1IpAkEAuhhnNIhHo+U1tSCaDWwlSVLCPtJoCm87lZEvyDxPGhjQdrOxinCNtENm\n" +
                "6rEHI36cZO8u91HdgntZYxMe4elWlQJAQ4u/Ww9W/5Rek5QmhlFrCDp2F4fM8HE6\n" +
                "Gcw/dRrwP/3py0M9E3zdfxkYGjA1jmZvBfQ2348oVX8PMt0b3N7lEQJAXnEO+vpI\n" +
                "pSwgGfggekclQrms5nfW/hux3f3VbjMUh7EzRD3hPcXLYBGn93gfOWbAllWLhSox\n" +
                "dRuLgiQ4chXKtQ==");
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

    /**
     * 初始化认证费参数数据
     *
     * @author guomw
     */
    @Transactional(rollbackFor = RuntimeException.class)
    public void initAuthFee() {
        Map<String, String> map = new HashMap<>();
        map.put("auth_backlist_bus_fee", "10");
        map.put("auth_backlist_finance_fee", "10");
        map.put("auth_carrier_fee", "10");
        map.put("auth_taobao_fee", "10");
        map.put("auth_jingdong_fee", "10");
        initMerchantConfigItem(map, MerchantConfigEnum.AUTH_FEE);
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

/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huotu.loanmarket.common.enums;

import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 枚举处理类
 *
 * @author Administrator
 */
public class EnumHelper {

    public static String getEnumName(Class<? extends ICommonEnum> cls, int value) {
        ICommonEnum ice = getEnumType(cls, value);
        if (ice != null) {
            return ice.getName();
//            try {
//                return new String(ice.getName().getBytes("UTF-8"));
//            } catch (UnsupportedEncodingException e) {
//                return "";
//            }
        }
        return "";
    }

    public static <T extends ICommonEnum> T getEnumType(Class<T> cls, Integer value) {
        if (value == null) {
            return null;
        }
        for (T item : cls.getEnumConstants()) {
            if (item.getCode() == value) {
                return item;
            }
        }
        return null;
    }

    public static <T extends IMerchantParameterEnum> T getEnumType(Class<T> cls, String name) {
        if (StringUtils.isEmpty(name)) {
            return null;
        }
        for (T item : cls.getEnumConstants()) {
            if (item.getKey().equals(name)) {
                return item;
            }
        }
        return null;
    }

    /**
     * 获取某个枚举类型的值列表
     * @param enumClass 枚举类型
     * @param <T>
     * @return
     */
    public static <T extends Enum<T> & ICommonEnum> List<KeyValueItem> getEnumList(Class<T> enumClass) {
        List<KeyValueItem> keyValueItemList = new ArrayList<>();
        Enum[] arrEnums = enumClass.getEnumConstants();
        for (Enum<T> e : arrEnums) {
            ICommonEnum commonEnum = (ICommonEnum)e;
            keyValueItemList.add(new KeyValueItem(commonEnum.getCode(),commonEnum.getName()));
        }
        return keyValueItemList;
    }
}

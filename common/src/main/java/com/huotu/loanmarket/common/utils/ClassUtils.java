package com.huotu.loanmarket.common.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * @author wm
 */
public class ClassUtils {
    /**
     * 将对象的属性和值放到map中
     */
    public static Map<String, String> getFieldMap(Object obj,boolean fieldNameToLower) {
        Map<String, String> map = new HashMap<>();
        if (obj == null) {
            return null;
        }
        Field[] fields = obj.getClass().getDeclaredFields();
        for (Field field : fields) {
            String fieldName = field.getName();
            if (getValueByFieldName(fieldName, obj) != null) {
                map.put(fieldNameToLower?fieldName.toLowerCase():fieldName, getValueByFieldName(fieldName, obj).toString());
            }
        }
        return map;
    }

    public static Map<String, String> getFieldMap(Object obj) {
        return getFieldMap(obj,false);
    }

    /**
     * 根据属性名获取该类此属性的值
     *
     * @param fieldName
     * @param object
     * @return
     */
    public static Object getValueByFieldName(String fieldName, Object object) {
        String firstLetter = fieldName.substring(0, 1).toUpperCase();
        String getter = "get" + firstLetter + fieldName.substring(1);
        try {
            Method method = object.getClass().getMethod(getter, new Class[]{});
            Object value = method.invoke(object, new Object[]{});
            return value;
        } catch (Exception e) {
            return null;
        }
    }
}
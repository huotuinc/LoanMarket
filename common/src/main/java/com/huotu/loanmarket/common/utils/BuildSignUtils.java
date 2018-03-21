/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼在地图中查看
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2016-2018. All rights reserved.
 */

package com.huotu.loanmarket.common.utils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @author guomw
 * @date 2018/3/20
 */
public class BuildSignUtils {

    private static final Log log = LogFactory.getLog(BuildSignUtils.class);
    /**
     * 创建一个sign签名
     * 忽略值为空的
     *
     * @param params 代签名参数，key排序的map
     * @param prefix 签名前缀
     * @param suffix 签名后缀
     * @return 返回鉴权信息字符串
     */
    @SuppressWarnings("unchecked")
    public static String buildSignIgnoreEmpty(Map<String, String> params, String prefix, String suffix) throws UnsupportedEncodingException {
        if (prefix == null) {
            prefix = "";
        }
        if (suffix == null) {
            suffix = "";
        }
        List arrayList = new ArrayList(params.entrySet());
        Collections.sort(
                arrayList, (arg1, arg2) -> {
                    Map.Entry obj1 = (Map.Entry) arg1;
                    Map.Entry obj2 = (Map.Entry) arg2;
                    return (obj1.getKey()).toString().toLowerCase().compareTo(
                            obj2.getKey().toString().toLowerCase()
                    );
                }
        );
        StringBuilder stringBuilder = new StringBuilder(prefix);
        for (Object anArrayList : arrayList) {
            Map.Entry entry = (Map.Entry) anArrayList;
            if (StringUtils.isEmpty(entry.getValue().toString())) {
                continue;
            }
            String key = (String) entry.getKey();
            stringBuilder.append(key.toLowerCase());
            stringBuilder.append(entry.getValue());
        }
        stringBuilder.append(suffix);
        log.debug("before sign:" + stringBuilder.toString());
        return DigestUtils.md5DigestAsHex(stringBuilder.toString().getBytes("UTF-8"));
    }

}

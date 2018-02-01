package com.huotu.loanmarket.common.utils;

import org.apache.http.client.utils.DateUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

/**
 * @author allan
 * @date 01/11/2017
 */
public class RandomUtils {
    /**
     * 产生在区间内的随机正整数，[min,max]
     *
     * @param min 最小数
     * @param max 最大数
     * @return 产生的随机数
     */
    public static int nextIntInSection(int min, int max) {
        final Random random = new Random();
        if (min > max) {
            throw new IllegalStateException("最小数不能超过最大数");
        }
        return random.nextInt(max - min + 1) + min;
    }

    /**
     * 产生在区间内的随机小数, [min,max)
     *
     * @param min 最小数
     * @param max 最大数
     * @return 随机小数
     */
    public static double nextDoubleInSection(int min, int max) {
        final Random random = new Random();
        if (min >= max) {
            throw new IllegalStateException("最小数必须小于最大数");
        }
        return random.nextDouble() * (max - min) + min;
    }

    /**
     * <p>位数不足无法保证其唯一性,需要客户端代码自行校验唯一性.</p>
     * <p>具体的区间是10000000000-19999999999</p>
     *
     * @return 获取一个随机的手机号码
     */
    public static String randomMobile() {
        final Random random = new Random();
        String p1 = String.valueOf(100000 + random.nextInt(100000));
        //还有5位 而且必须保证5位
        String p2 = String.format("%05d", random.nextInt(100000));
        return p1 + p2;
    }

    /**
     * @return 尽可能唯一的随机字符串
     * @since 2.2
     */
    public static String randomString() {
        return UUID.randomUUID().toString();
    }

    public static String getTransactionId() {
        Date now = new Date();
        String time = DateUtils.formatDate(now, "yyyyMMddhhmmssSSS");
        String str = getString(13);
        return time + str;
    }

    /**
     * 随机生成指定位数的字符串（去掉0开头）
     *
     * @param length
     * @return
     */
    public static String getString(int length) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < length; i++) {
            // 产生0-10之间的随机小数，强制转换成正数
            int num = (int) (Math.random() * 10);
            if (i == 0) {
                if (num == 0) {
                    num = num + 1;
                }
            }
            stringBuilder.append(num);
        }
        return stringBuilder.toString();
    }

    /**
     * 生成借款单号(时间yyyyMMddHHmmss+13位随机数)
     * 例如：20171117155412
     **/
    public static String randomDateTimeString() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Date date = new Date();
        return sdf.format(date) + getString(19);
    }

}

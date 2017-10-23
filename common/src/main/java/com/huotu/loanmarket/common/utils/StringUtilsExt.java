/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2017. All rights reserved.
 */

package com.huotu.loanmarket.common.utils;

import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author allan
 */
public class StringUtilsExt {
    public static final String NETSHOP_SECRET = "123456";
    public static String DATE_PATTERN = "yyyy-MM-dd";
    public static String TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
    public static String TIME_PATTERN2 = "yyyyMMddHHmmss";
    public static String ENCODING_UTF8 = "utf-8";

    public StringUtilsExt() {
    }

    public static boolean isNull(Object pobjObject) {
        if (pobjObject == null) {
            return true;
        }
        if (pobjObject instanceof String) {
            String s = (String) pobjObject;
            if (StringUtilsExt.trim(StringUtilsExt.trimMulti(s, false)).trim().length() < 1) {
                return true;
            }
        }
        return false;
    }

    public static boolean isNotNull(Object pobjObject) {
        return !isNull(pobjObject);
    }

    /**
     * 将字符串按照制度文字切割为List?<br>
     * <br>
     *
     * @param pstrString 源字符串
     * @param pstrSep    切割文字
     * @return 切割后的List
     */

    public static List<String> splitStringToList(String pstrString, String pstrSep) {
        if (pstrString == null || pstrSep == null) {
            return new ArrayList<String>();
        }

        return new ArrayList<String>(Arrays.asList(pstrString.split(pstrSep)));
    }

    public static String trim(String value) {
        if (value == null) {
            return null;
        }
        return value.replaceAll("\\A[\\u0000-\\u0020?]+", "").replaceAll("[\\u0000-\\u0020?]+\\z", "");
    }

    public static String trimMulti(String value, boolean removeBlankLine) {
        if (value == null) {
            return null;
        }
        String excludeLineFeed = "&&[^\\n\\r]";
        if (removeBlankLine) {
            excludeLineFeed = "";
        }
        return value.replaceAll("(?m)^[\\u0000-\\u0020?" + excludeLineFeed + "]+", "").replaceAll(
                "(?m)[\\u0000-\\u0020?" + excludeLineFeed + "]+$", "");
    }

    /**
     * 将编码转化成通用的ISO编码，?合将输入字符转换成中文
     *
     * @param str String 输入字符?
     * @return String
     */
    public static String getStr(String str) {
        try {
            String temp_p = str;
            byte[] temp_t = temp_p.getBytes("ISO8859-1");
            String temp = new String(temp_t);
            return temp;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 相当于重写String.valueOf，如果object为空，不返回"null"，而返回null
     *
     * @param object
     * @return
     */
    public static String valueOf(Object object) {
        if (object == null) {
            return null;
        }
        return String.valueOf(object);
    }

    /**
     * 将输入字符串<param>input</param>中的'转换? \'以及"转化成\". 这样不会引起脚本歧义? 可以直接在JS中应用??
     *
     * @param input String
     * @return String
     */
    public static String escapeJSTags(String input) {
        if (input == null || input.length() == 0)
            return input;
        StringBuffer buf = new StringBuffer();
        char ch = ' ';
        for (int i = 0; i < input.length(); i++) {
            ch = input.charAt(i);
            if (ch == '\'') {
                buf.append("\\'");
                continue;
            }
            if (ch == '"') {
                buf.append("\"");
                continue;
            }
            if (ch == '\r') {
                continue;
            }
            if (ch == '\n') {
                continue;
            }
            if (ch == '\\') {
                buf.append("\\\\");
                continue;
            }
            buf.append(ch);
        }
        return buf.toString();

    }

    /**
     * Escape SQL tags, ' to '';
     *
     * @param input string to replace
     * @return string
     */
    public static String escapeSQLTags(String input) {
        if (input == null || input.length() == 0) {
            return input;
        }
        StringBuffer buf = new StringBuffer();
        char ch = ' ';
        for (int i = 0; i < input.length(); i++) {
            ch = input.charAt(i);
            if (ch == '\'') {
                buf.append("\'\'");
            } else {
                buf.append(ch);
            }
        }
        return buf.toString();
    }

    /**
     * 判断字符?<param>str</param>是哪种语?的文字??
     * <LI>根据unicode编码，如果字符串中的某个字符值在<br>
     * 汉字? Unicode 里面有单独的几块区域，是中日韩（朝鲜）共享的。以下两? U+4e00 ~ U+9FBB 原来 GB2312 ? GBK
     * 中的汉字 U+3400 ~ U+4DB6 包括 GB18030.2000 中那些增加的汉字 <br>
     * 之间，则字符串中含有中文?
     * <LI>?些中文符号属? CJK Symbols and Punctuation 范围，从 u+3000 ? u+303F
     * <LI>生成命令信件时，应该使用方法 <code>toGBStringBinary</code>进行转换?<strong>注意参数必须是unicode编码才行<strong>
     * <LI>由于目前系统考虑只支持中英文，其它情况一律认为是英文，使用方? <code>toISOStringBinary</code>进行转换?
     *
     * @param str 被验证的字符?
     * @return 含中文字符串?"zh"，其它的字符串认为是英文字符串，返回:"en"
     */
    public static String getLanguageFromStr(String str) {
        int len;
        len = (str == null) ? 0 : str.length();
        char ch;
        for (int i = 0; i < len; i++) {
            ch = str.charAt(i);
            int value = (int) ch;
            // 中文判别
            if ((value >= 0x4e00 && value <= 0x9fbb)
                    || (value >= 0x3400 && value <= 0x4db6)
                    || (value >= 0x3000 && value <= 0x303F)) {
                return "zh";
            }
        }
        return "en";
    }

    /**
     * 全角转半角的方法
     *
     * @param QJstr 输入的字符串
     * @return 转换成半角的字符?
     */
    public static final String SBCchange(String QJstr) {
        if (QJstr == null || QJstr.trim().length() == 0) {
            return QJstr;
        }
        String outStr = "";
        String Tstr = "";
        byte[] b = null;

        for (int i = 0; i < QJstr.length(); i++) {
            try {
                Tstr = QJstr.substring(i, i + 1);
                b = Tstr.getBytes("unicode");
            } catch (UnsupportedEncodingException e) {

                e.printStackTrace();
            }

            if (b[3] == -1) {
                b[2] = (byte) (b[2] + 32);
                b[3] = 0;

                try {
                    outStr = outStr + new String(b, "unicode");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            } else {
                outStr = outStr + Tstr;
            }
        }

        return outStr;
    }

    /**
     * 根据表格宽度解决字符串换行问题
     *
     * @param locale
     * @param tableWidth
     * @param str
     * @return
     * @author meiyuhai
     */
    public static String newLine(Locale locale, int tableWidth, String str) {
        if (locale.getLanguage().equalsIgnoreCase("zh")) {
            return str;
        }
        int tWidth = tableWidth;
        String strContent = str;
        int lineNum = tWidth / 6;
        String strTemp = "";
        String temp = "";
        try {
            while (strContent.length() > lineNum) {
                temp = strContent.substring(0, lineNum - 1);
                int index = temp.lastIndexOf(" ");
                if (index == -1) {
                    return str;
                }
                strTemp = strTemp + strContent.substring(0, index) + "<br>";
                strContent = strContent.substring(index, strContent.length());
            }
            strTemp = strTemp + strContent;
            return strTemp.trim();
        } catch (IndexOutOfBoundsException e) {

            e.printStackTrace();
            return str;
        }
    }

    /**
     * 判断str是否为空
     *
     * @param str 要判断的字符
     * @return str为null或?为""则返回true,否则返回false
     */
    public static boolean isEmptyStr(String str) {
        if (str == null)
            return true;
        if ("".equals(str.trim()))
            return true;
        return false;
    }

    /**
     * 验证空串
     *
     * @param
     * @return
     */
    public static boolean isEmpty(Object checkStr_) {
        if (checkStr_ == null) return true;
        String checkStr = checkStr_.toString();
        return checkStr == null ? true : (checkStr.trim().length() == 0
                || "null".equalsIgnoreCase(checkStr.trim()) ? true : false);
    }

    /**
     * 是否不为?
     *
     * @param str
     * @return
     */
    public static boolean isNotEmpty(String str) {
        if (str != null) {
            if (str.length() == 0) {
                return false;
            } else {
                return true;
            }
        }
        return false;
    }


    /**
     * 字符串MD5形式加密?<BR>
     * <br>
     * 字符串为null或不能加密的情况下返回null?
     *
     * @param aString 对象字符?
     * @return 加密后的字符?
     */

    public static String encrypt(String aString) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            byte[] tempBytes = messageDigest.digest(aString.getBytes("US-ASCII"));
            StringBuffer stringBuffer = new StringBuffer();
            for (int i = tempBytes.length - 1; i >= 0; i--) {
                String tempByte = Integer.toHexString(tempBytes[i] & 0xFF);
                if (tempByte.length() == 1) {
                    stringBuffer.append("0");
                }
                stringBuffer.append(tempByte);
            }
            return stringBuffer.toString().toUpperCase();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将null的字符串转换?""
     *
     * @param str
     * @return
     */
    public static String getNullStr(String str) {
        if (str == null) {
            return "";
        }

        if (str.equals("null") || str.equals("NULL")) {
            return "";
        } else {
            return str;
        }
    }

    public static String getNull(Object obj) {
        if (obj == null) {
            return "";
        }
        return getNullStr(obj.toString());
    }

    /**
     * 将string转换为int，转换异常则返回0
     *
     * @param str
     * @return
     */
    public static int getIntOfStr(String str) {
        try {
            return Integer.parseInt(str.trim());
        } catch (Exception ingore) {
            return 0;
        }
    }

    /**
     * 将object转换为int，转换异常则返回0
     *
     * @param obj
     * @return
     */
    public static int getIntOfObj(Object obj) {
        try {
            return Integer.parseInt(obj.toString().trim());
        } catch (Exception ingore) {
            return 0;
        }
    }

    /**
     * 04	     * 检测是否有emoji字符
     * 05	     * @param source
     * 06	     * @return 一旦含有就抛出
     * 07
     */
    public static boolean containsEmoji(String source) {
        if (StringUtils.isEmpty(source)) {
            return false;
        }

        int len = source.length();

        for (int i = 0; i < len; i++) {
            char codePoint = source.charAt(i);

            if (isEmojiCharacter(codePoint)) {
                //do nothing，判断到了这里表明，确认有表情字符
                return true;
            }
        }

        return false;
    }


    private static boolean isEmojiCharacter(char codePoint) {
        return (codePoint == 0x0) ||
                (codePoint == 0x9) ||
                (codePoint == 0xA) ||
                (codePoint == 0xD) ||
                ((codePoint >= 0x20) && (codePoint <= 0xD7FF)) ||
                ((codePoint >= 0xE000) && (codePoint <= 0xFFFD)) ||
                ((codePoint >= 0x10000) && (codePoint <= 0x10FFFF));
    }

    /**
     * 38	     * 过滤emoji 或者 其他非文字类型的字符
     * 39	     * @param source
     * 40	     * @return
     * 41
     */
    public static String filterEmoji(String source) {

        if (!containsEmoji(source)) {
            return source;//如果不包含，直接返回
        }
        //到这里铁定包含
        StringBuilder buf = null;

        int len = source.length();

        for (int i = 0; i < len; i++) {
            char codePoint = source.charAt(i);

            if (isEmojiCharacter(codePoint)) {
                if (buf == null) {
                    buf = new StringBuilder(source.length());
                }

                buf.append(codePoint);
            }
        }

        if (buf == null) {
            return source;//如果没有找到 emoji表情，则返回源字符串
        } else {
            if (buf.length() == len) {//这里的意义在于尽可能少的toString，因为会重新生成字符串
                buf = null;
                return source;
            } else {
                return buf.toString();
            }
        }

    }

    /**
     * 将object转换为double，转换异常则返回0
     *
     * @param obj
     * @return
     */
    public static double getDoubleOfObj(Object obj) {
        try {
            return Double.parseDouble(obj.toString().trim());
        } catch (Exception ingore) {
            return 0;
        }
    }

    public static String replace(String template, String placeholder,
                                 String replacement) {
        return replace(template, placeholder, replacement, false);
    }

    public static String replace(String template, String placeholder,
                                 String replacement, boolean wholeWords) {
        int loc = template.indexOf(placeholder);
        if (loc < 0) {
            return template;
        } else {
            final boolean actuallyReplace = !wholeWords
                    || loc + placeholder.length() == template.length()
                    || !Character.isJavaIdentifierPart(template.charAt(loc
                    + placeholder.length()));
            String actualReplacement = actuallyReplace ? replacement
                    : placeholder;
            return new StringBuffer(template.substring(0, loc)).append(
                    actualReplacement).append(
                    replace(template.substring(loc + placeholder.length()),
                            placeholder, replacement, wholeWords)).toString();
        }
    }

    /**
     * 将一个时间字符串格式化为另一个时间字符串
     *
     * @param dateStr
     * @param oldPattern
     * @param newPattern
     * @return
     */
    public static String dateFormat(String dateStr, String oldPattern, String newPattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(oldPattern);

        Date date = null;

        try {
            date = sdf.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }

        return new SimpleDateFormat(newPattern).format(date);
    }

    /**
     * 更具规则格式化时?
     *
     * @param date
     * @param newPattern
     * @return
     */
    public static String dateFormat(Date date, String newPattern) {
        if (date == null) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(newPattern);

        return sdf.format(date);
    }

    /**
     * 根据字符串格式化成时?
     *
     * @param dateStr
     * @param pattern
     * @return
     */
    public static Date dateFormat(String dateStr, String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        Date date = null;
        try {
            date = sdf.parse(dateStr);
        } catch (ParseException e) {
        }

        return date;
    }

    /**
     * 得到随机字符串并md5加密
     *
     * @return
     */
    public static String createRandomStr32() {
        long timestamp = System.currentTimeMillis();
        String str = String.valueOf(timestamp + (int) Math.random() * 100);
        try {
            return DigestUtils.md5DigestAsHex(str.getBytes("utf-8"));
        } catch (UnsupportedEncodingException e) {
            throw new InternalError();
        }
    }

    /**
     * 得到随机字符串
     *
     * @return
     */
    public static String createRandomStr(int digit) {
        String base = "abcdefghijklmnopqrstuvwxyz0123456789";
        int baseLength = base.length();
        Random random = new Random();
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < digit; i++) {
            int number = random.nextInt(baseLength);
            stringBuilder.append(base.charAt(number));
        }
        return stringBuilder.toString();
    }

    /**
     * 如果为空，则返回默认数值
     *
     * @param number
     * @param defNumber
     * @return
     */
    public static Integer getWithDefault(Object number, Integer defNumber) {
        if (StringUtilsExt.isEmpty(number)) {
            return defNumber;
        }
        return Integer.valueOf(number.toString());
    }

    public static Double getWithDefault(Object object, Double defNumber) {
        if (StringUtilsExt.isEmpty(object)) {
            return defNumber;
        }
        return (double) object;
    }

    public static String getWithDefault(Object object, String defStr) {
        if (StringUtilsExt.isEmpty(object)) {
            return defStr;
        }
        return String.valueOf(object);
    }

    /**
     * 得到流水号，默认20位，最少14位
     *
     * @param lenth 额外位数
     * @return
     */
    public static String createFlowNo(int lenth) {
        String prefix = dateFormat(new Date(), "yyyyMMddHHmmss");

        Random random = new Random();
        StringBuilder suffix = new StringBuilder();
        //随机产生后几位数字的字符串
        for (int i = 0; i < lenth; i++) {
            String rand = String.valueOf(random.nextInt(10));
            suffix.append(rand);
        }

        return prefix + suffix.toString();
    }

    /**
     * 字符串转日期
     *
     * @param str
     * @return
     */
    public static Date stringToDate(String str) {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date date = null;
        try {
            date = format.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }
}

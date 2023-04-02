package com.chitchat.common.util;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.StrUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.lang.reflect.Field;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Js on 2022/7/23.
 */
public class StringUtil extends StringUtils {

    public static int size(String str) {
        return isEmpty(str) ? 0 : str.length();
    }

    public static boolean isEmpty(String value) {
        return value == null || value.trim().length() == 0;
    }

    public static boolean isEmpty(Object[] array) {
        return array == null || array.length == 0;
    }

    public static boolean isEmpty(StringBuffer sb) {
        return sb == null || sb.length() == 0;
    }

    /**
     * * 判断一个对象是否为空
     *
     * @param object Object
     * @return true：为空 false：非空
     */
    public static boolean isNull(Object object)
    {
        return object == null;
    }

    /**
     * * 判断一个对象是否非空
     *
     * @param object Object
     * @return true：非空 false：空
     */
    public static boolean isNotNull(Object object)
    {
        return !isNull(object);
    }

    public static String format(String value) {
        return format(value, "");
    }

    public static String format(String value, String defaultValue) {
        return isEmpty(value) ? defaultValue : value.trim();
    }

    //Object转Map
    public static Map<String, Object> getObjectToMap(Object obj) throws IllegalAccessException {
        Map<String, Object> map = new LinkedHashMap<String, Object>();
        Class<?> clazz = obj.getClass();
        System.out.println(clazz);
        for (Field field : clazz.getDeclaredFields()) {
            field.setAccessible(true);
            String fieldName = field.getName();
            Object value = field.get(obj);
            if (value == null) {
                value = "";
            }
            map.put(fieldName, value);
        }
        return map;
    }

    public static String formatString(String str, String... params) {
        for (int i = 0; i < params.length; ++i) {
            str = str.replace("{" + i + "}", params[i] == null ? "" : params[i]);
        }

        return str;
    }

    public static final int parseInt(String value) {
        return isInt(value) ? Integer.parseInt(value) : 0;
    }

    public static final int parseInt(Integer value) {
        return value != null ? value : 0;
    }

    public static final boolean isInt(String value) {
        if (isEmpty(value)) {
            return false;
        } else {
            try {
                Integer.parseInt(value);
                return true;
            } catch (NumberFormatException var2) {
                return false;
            }
        }
    }

    public static String camel4underline(String param) {
        Pattern p = Pattern.compile("[A-Z]");
        if (param != null && !param.equals("")) {
            StringBuilder builder = new StringBuilder(param);
            Matcher mc = p.matcher(param);

            for (int i = 0; mc.find(); ++i) {
                builder.replace(mc.start() + i, mc.end() + i, "_" + mc.group().toLowerCase());
            }

            if ('_' == builder.charAt(0)) {
                builder.deleteCharAt(0);
            }

            return builder.toString();
        } else {
            return "";
        }
    }

    public static String getUUID() {
        return UUID.randomUUID().toString();
    }


    public static String stringArrayToString(String[] str, String separator) {
        StringBuffer sb = new StringBuffer();

        for (int i = 0; i < str.length; ++i) {
            if (!isEmpty(str[i])) {
                sb.append(str[i]);
                if (str.length != i + 1) {
                    sb.append(separator);
                }
            }
        }

        return sb.toString();
    }

    public static String stringArrayToString(String[] str) {
        return stringArrayToString(str, "");
    }

    public static String charArrayToString(char[] ch, String separator) {
        StringBuffer sb = new StringBuffer();

        for (int i = 0; i < ch.length; ++i) {
            sb.append(ch[i]);
            if (ch.length != i + 1) {
                sb.append(separator);
            }
        }

        return sb.toString();
    }

    public static String charArrayToString(char[] ch) {
        return charArrayToString(ch, " ");
    }

    public static String substrGB(String text, int length) {
        String sRet = "";
        if (isEmpty(text)) {
            return "";
        } else {
            if (text.length() <= length) {
                sRet = text;
            } else {
                sRet = text.substring(0, length) + "...";
            }

            return sRet;
        }
    }

    public static String substr(String text, int length) {
        String sRet = "";
        if (isEmpty(text)) {
            return "";
        } else {
            if (text.length() <= length) {
                sRet = text;
            } else {
                sRet = text.substring(0, length);
            }

            return sRet;
        }
    }

    public static String format(String str, boolean format) {
        str = format(str);
        if (isMessyCode(str)) {
            try {
                return new String(str.getBytes("ISO8859-1"), "UTF-8");
            } catch (Exception var3) {
                return "";
            }
        } else {
            return str;
        }
    }

    public static boolean isMessyCode(String strName) {
        Pattern p = Pattern.compile("\\s*|\t*|\r*|\n*");
        Matcher m = p.matcher(strName);
        String after = m.replaceAll("");
        String temp = after.replaceAll("\\p{P}", "");
        char[] ch = temp.trim().toCharArray();
        float chLength = 0.0F;
        float count = 0.0F;

        for (int i = 0; i < ch.length; ++i) {
            char c = ch[i];
            if (!Character.isLetterOrDigit(c)) {
                if (!isChinese(c)) {
                    ++count;
                }

                ++chLength;
            }
        }

        float result = count / chLength;
        return (double) result > 0.4D;
    }

    private static boolean isChinese(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        return ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS;
    }

    public static final String getRandomString(int length) {
        String[] s = new String[]{"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
        if (length < 1) {
            return "";
        } else {
            StringBuffer sb = new StringBuffer(length);

            for (int i = 0; i < length; ++i) {
                int position = getRandomNumber(s.length - 1);
                Collections.shuffle(Arrays.asList(s));
                sb.append(s[position]);
            }

            return sb.toString();
        }
    }

    public static final int getRandomNumber(int max) {
        return getRandomNumber(0, max);
    }

    public static final int getRandomNumber(int min, int max) {
        if (min > max) {
            int k = min;
            min = max;
            max = k;
        }

        Random random = new Random();
        return random.nextInt(max - min) + min;
    }

    /**
     * 验证手机号码格式是否正确
     *
     * @param mobiles
     * @return true 表示正确  false表示不正确
     * 2019-10
     * ^1([38]\d|4[5-9]|5[0-35-9]|6[2567]|7[0-8]|9[13589])\d{8}$
     * ^((13[0-9])|(15[^4,\D])|(18[0,5-9]))\d{8}$
     */
    public static boolean isMobileNO(String mobiles) {
        Pattern p = Pattern.compile("^1([38]\\d|4[5-9]|5[0-35-9]|6[2567]|7[0-8]|9[13589])\\d{8}$");
        Matcher m = p.matcher(mobiles);
        return m.matches();
    }

    /**
     * 验证手机号码格式是否正确（前两位）
     *
     * @param mobiles
     * @return
     */
    public static boolean isMobileNOThird(String mobiles) {
        Pattern p = Pattern.compile("^1[3-9]\\d{9}$");
        Matcher m = p.matcher(mobiles);
        return m.matches();
    }


    private static final char UNDERLINE = '_';

    /**
     * 下划线 转 驼峰
     *
     * @param param
     * @return
     */
    public static String underlineToCamel(String param) {

        if (StringUtil.isBlank(param)) {
            return "";
        }
        int len = param.length();
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            char c = param.charAt(i);
            if (c == UNDERLINE) {
                if (++i < len) {
                    sb.append(Character.toUpperCase(param.charAt(i)));
                }
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    /**
     * 将下划线大写方式命名的字符串转换为驼峰式。如果转换前的下划线大写方式命名的字符串为空，则返回空字符串。
     * 例如：HELLO_WORLD->HelloWorld
     *
     * @param name 转换前的下划线大写方式命名的字符串
     * @return 转换后的驼峰式命名的字符串
     */
    public static String camelName(String name) {
        StringBuilder result = new StringBuilder();
        // 快速检查
        if (name == null || name.isEmpty()) {
            // 没必要转换
            return "";
        } else if (!name.contains("_")) {
            // 不含下划线，仅将首字母小写
            return name.substring(0, 1).toLowerCase() + name.substring(1);
        }
        // 用下划线将原始字符串分割
        String camels[] = name.split("_");
        for (String camel : camels) {
            // 跳过原始字符串中开头、结尾的下换线或双重下划线
            if (camel.isEmpty()) {
                continue;
            }
            // 处理真正的驼峰片段
            if (result.length() == 0) {
                // 第一个驼峰片段，全部字母都小写
                result.append(camel.toLowerCase());
            } else {
                // 其他的驼峰片段，首字母大写
                result.append(camel.substring(0, 1).toUpperCase());
                result.append(camel.substring(1).toLowerCase());
            }
        }
        return result.toString();

    }

    public static String getAtStringByArray(String... strings) {
        StringBuffer sb = new StringBuffer();
        for (String str : strings) {
            sb.append(str).append("@");
        }
        return sb.toString();
    }

    public static String getAtStringByArray(Integer... strings) {
        StringBuffer sb = new StringBuffer();
        for (Integer str : strings) {
            if (str == null) {
                continue;
            }
            sb.append(str).append("@");
        }
        return sb.toString();
    }


    private static final long ONE_MINUTE = 60000L;
    private static final long ONE_HOUR = 3600000L;
    private static final long ONE_DAY = 86400000L;
    private static final long ONE_WEEK = 604800000L;

    private static final String ONE_SECOND_AGO = "秒前";
    private static final String ONE_MINUTE_AGO = "分钟前";
    private static final String ONE_HOUR_AGO = "小时前";
    private static final String ONE_DAY_AGO = "天前";
    private static final String ONE_MONTH_AGO = "月前";
    private static final String ONE_YEAR_AGO = "年前";

    public static String relativeDateFormat(Date date) {
        long delta = new Date().getTime() - date.getTime();
        if (delta < 1L * ONE_MINUTE) {
            long seconds = toSeconds(delta);
            return (seconds <= 0 ? 1 : seconds) + ONE_SECOND_AGO;
        }
        if (delta < 45L * ONE_MINUTE) {
            long minutes = toMinutes(delta);
            return (minutes <= 0 ? 1 : minutes) + ONE_MINUTE_AGO;
        }
        if (delta < 24L * ONE_HOUR) {
            long hours = toHours(delta);
            return (hours <= 0 ? 1 : hours) + ONE_HOUR_AGO;
        }
        if (delta < 48L * ONE_HOUR) {
            return "昨天";
        }
        if (delta < 30L * ONE_DAY) {
            long days = toDays(delta);
            return (days <= 0 ? 1 : days) + ONE_DAY_AGO;
        }
        if (delta < 12L * 4L * ONE_WEEK) {
            long months = toMonths(delta);
            return (months <= 0 ? 1 : months) + ONE_MONTH_AGO;
        } else {
            long years = toYears(delta);
            return (years <= 0 ? 1 : years) + ONE_YEAR_AGO;
        }
    }

    private static long toSeconds(long date) {
        return date / 1000L;
    }

    private static long toMinutes(long date) {
        return toSeconds(date) / 60L;
    }

    private static long toHours(long date) {
        return toMinutes(date) / 60L;
    }

    private static long toDays(long date) {
        return toHours(date) / 24L;
    }

    private static long toMonths(long date) {
        return toDays(date) / 30L;
    }

    private static long toYears(long date) {
        return toMonths(date) / 12L;
    }

    /**
     * 判断是否为数字
     *
     * @param str
     * @param i
     * @return
     */
    public static String judgeCode(String str, Integer i) {
        //如果不包含字母.*[A-Za-z]+.*
        if (Pattern.matches("[0-9]*", str)) {
            return str;
        } else {
            //首位不是数字，数量编号
            return str + i;
        }
    }

    /**
     * 编号 + 1
     *
     * @param str
     * @return
     */
    public static String getVersionCode(String str) {
        String code = "";
        if (StrUtil.isBlank(str)) {
            return "001";
        }
        //如果不包含字母.*[A-Za-z]+.*
        if (Pattern.matches("[0-9]*", str)) {
            int length = str.length();
            String newStr = str.replaceAll("^(0+)", "");
            code = String.format("%0" + length + "d", Integer.parseInt(newStr) + 1);

        } else {
            //首位不是数字，数量编号
            return str;
        }
        return code;
    }

    /**
     * 字符串拼接 下划线 _
     *
     * @param str
     * @return
     */
    public static String judgeLastChar(String str) {

        String s = str.substring(str.length() - 1);
        if (s.equals("_")) {
            return str;
        } else {
            return str + "_";
        }
    }

    public static String redisKey(Class c, int key) {
        return c.getName() + ":" + key;
    }


    public static String removeLastChar(String str, char ch) {
        boolean flag = true;
        do {
            char lastChar = str.charAt(str.length() - 1);
            if (lastChar == ch) {
                str = str.substring(0, str.length() - 1);
            } else {
                flag = false;
            }
        } while (flag);
        return str;
    }

    /**
     * 判断字符串是否只包含字母和下划线
     *
     * @param str
     * @return
     */
    public static boolean isSpecialStr(String str) {
        return str.trim().matches("[A-Za-z_]*");
    }

    /**
     * 手机号码前三后四脱敏
     *
     * @param mobile
     * @return
     */
    public static String mobileEncrypt(String mobile) {
        if (StringUtils.isEmpty(mobile) || (mobile.length() != 11)) {
            return mobile;
        }
        return mobile.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");
    }

    /**
     * 判断字符串是否只包含字母和数组
     *
     * @param str
     * @return
     */
    public static boolean isNumOrABC(String str) {
        return str.trim().matches("[0-9A-Za-z]*");
    }

    /**
     * 上传的目录
     * 目录: 根据年月日归档
     * 文件名: UUID
     *
     * @param fileName
     * @return
     */
    public static String getFilePath(String fileName) {
        DateTime dateTime = new DateTime();
        return dateTime.toString("yyyy") + "/" + dateTime.toString("MM") + "/"
                + dateTime.toString("dd") + "/" + IdUtil.randomUUID() + "." + StringUtils.substringAfterLast(fileName, ".");
    }

    /**
     * 上传的目录
     * 目录: 根据年月日归档
     *
     * @param fileName
     * @return
     */
    public static String getFilePathByDate(String fileName) {
        DateTime dateTime = new DateTime();
        return dateTime.toString("yyyyMMdd") + "/" + IdUtil.randomUUID() + "/" + fileName;
    }


    public static String getExtractPath(final HttpServletRequest request) {
        String path = (String) request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);
        String bestMatchPattern = (String) request.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE);
        return new AntPathMatcher().extractPathWithinPattern(bestMatchPattern, path);
    }

    /**
     * 判断目录是否正规【\ / * : ? < > " |】
     *
     * @param str
     * @return
     */
    public static boolean judgeDir(String str) {
        Pattern pattern = Pattern.compile("[\\\\:/*?\"<>|]");
        return pattern.matcher(str).find();
    }

    /**
     * 后缀名
     *
     * @param fileName
     * @return
     */
    public static String getFileSuffix(String fileName) {
        if (!fileName.contains(".")) {
            return null;
        }
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }

    /**
     * 字符串拼接
     *
     * @param str
     * @return
     */
    public static String concatString(String result, String str, String separator) {
        StringBuilder sb = new StringBuilder();
        if (isEmpty(str) && isEmpty(result)) {
            return null;
        } else if (isEmpty(result) && isNotBlank(str)) {
            sb.append(str);
        } else if (isNotBlank(result) && isEmpty(str)) {
            sb.append(result);
        } else {
            sb.append(result).append(separator).append(str);
        }
        return sb.toString();
    }

    /**
     * 根据固定步长累加
     *
     * @return
     */
    public static int add(Integer currentNum, Integer startNum, Integer var2, int i) {
        if (i == 0) {
            return startNum.intValue();
        }
        return NumberUtil.add(currentNum, var2).intValue();
    }

    /**
     * 拼接字符
     *
     * @param str
     * @param suffix
     * @return
     */
    public static String joinString(String str, Integer suffix) {
        int num = StrUtil.count(str, "#");
        String newStr = str.replaceAll("#", "");
        return num > 0 ? String.format(newStr + "%0" + (num + 1) + "d", suffix) : newStr + suffix;
    }

    public static void continter(String dir, String content) {
        File file = new File(dir);
        if (file.isDirectory()) {
            for (File listFile : file.listFiles()) {
                String c = FileUtil.readString(listFile, "utf-8");
                if (c.contains(content)) {
                    System.out.println(listFile.getAbsolutePath());
                    FileUtil.writeString(listFile.getAbsolutePath() + "\t", "d:\\error.txt", "utf-8");
                }
            }
        }


    }

}

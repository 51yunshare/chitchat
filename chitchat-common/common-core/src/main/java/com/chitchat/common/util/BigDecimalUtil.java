package com.chitchat.common.util;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * BigDeciaml比较工具类
 */
public class BigDecimalUtil {
    private static final BigDecimal zero = new BigDecimal(0);

    /**
     * 是否小于等于0
     */
    public static boolean isEltZero(BigDecimal value) {
        if (value == null) {
            return false;
        }
        return value.compareTo(zero) == 0 || value.compareTo(zero) == -1;
    }

    public static boolean isEq(BigDecimal value1, BigDecimal value2) {
        if (value1 == null || value2 == null) {
            return false;
        }
        if (value1.compareTo(value2) == 0) {
            return true;
        }
        return false;
    }

    /**
     * 判断对象内是否有空属性
     *
     * @param obj
     * @return
     */
    public static boolean checkObjFieldIsNull(Object obj) {

        boolean flag = false;
        try {
            for (Field f : obj.getClass().getDeclaredFields()) {
                f.setAccessible(true);
                if (f.get(obj) == null || f.get(obj).equals("")) {
                    flag = true;
                    return flag;
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 判断是是否所有字段为空
     *
     * @param obj
     * @return
     */
    public static boolean isAllFieldNull(Object obj) {
        Class stuCla = (Class) obj.getClass();// 得到类对象
        Field[] fs = stuCla.getDeclaredFields();//得到属性集合
        boolean flag = true;
        for (Field f : fs) {//遍历属性
            f.setAccessible(true); // 设置属性是可以访问的(私有的也可以)
            Object val = null;// 得到此属性的值
            try {
                val = f.get(obj);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            if (val != null) {//只要有1个属性不为空,那么就不是所有的属性值都为空
                flag = false;
                break;
            }
        }
        return flag;
    }

    /**
     * 获取某个数的负数
     *
     * @param num
     * @return
     */
    public static BigDecimal getMinus(BigDecimal num) {
        return num.abs().multiply(new BigDecimal("-1"));
    }

    /**
     * 获取2个数的差额
     * @param num1
     * @param num2
     * @return
     */
    public static BigDecimal difference(BigDecimal num1 , BigDecimal num2){
        return num1.subtract(num2).abs();
    }

    /**
     * 将source分成按n分成多个集合
     *
     * @param source
     * @param n
     * @param <T>
     * @return
     */
    public static <T> List<List<T>> fixedGrouping(List<T> source, int n) {

        if (null == source || source.size() == 0 || n <= 0)
            return null;
        List<List<T>> result = new ArrayList<List<T>>();

        int sourceSize = source.size();
        int size = (source.size() / n) + 1;
        for (int i = 0; i < size; i++) {
            List<T> subset = new ArrayList<T>();
            for (int j = i * n; j < (i + 1) * n; j++) {
                if (j < sourceSize) {
                    subset.add(source.get(j));
                }
            }
            result.add(subset);
        }
        return result;
    }

    public static boolean equal(BigDecimal o1 , BigDecimal o2){
        if(o1 == null || o2 == null){
            return false;
        }
        return o1.compareTo(o2) == 0;
    }


}

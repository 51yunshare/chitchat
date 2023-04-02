package com.chitchat.common.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * 通过反射获得 属性名（字段）以及属性（字段）的值
 */
public class ObjectClassUtil {
    /**
     *获得对象的字段（属性）
     * @param o
     * @return
     */
    public static String[] getFiledName(Object o) {
        Field[] fields = o.getClass().getDeclaredFields();
        String[] fieldNames = new String[fields.length];
        for (int i = 0; i < fields.length; i++) {
            fieldNames[i] = fields[i].getName();
        }
        return fieldNames;
    }

    /**
     * 获得属性字段的值
     * @param fieldName
     * @param o
     * @return
     */
    public static Object getFieldValueByName(String fieldName, Object o) {
        try {
            String firstLetter = fieldName.substring(0, 1).toUpperCase();
            String getter = "get" + firstLetter + fieldName.substring(1);
            Method method = o.getClass().getMethod(getter, new Class[] {});
            Object value = method.invoke(o, new Object[] {});
            return value;
        } catch (Exception e) {
          //  logger.error("获取属性值失败！" + e, e);
        }
        return null;
    }
}

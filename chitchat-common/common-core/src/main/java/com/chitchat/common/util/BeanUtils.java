package com.chitchat.common.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Js on 2022/7/25.
 */
@Slf4j
public final class BeanUtils {

    public static <T> T copyProperties(Object source , Class<T> t){
        try {
            T o = t.newInstance();
            org.springframework.beans.BeanUtils.copyProperties(source , o);
            return o;
        } catch (InstantiationException e) {
            log.error("转换出现异常{}", ExceptionUtils.getStackTrace(e));
        } catch (IllegalAccessException e) {
            log.error("转换出现异常{}", ExceptionUtils.getStackTrace(e));
        }
        return null;
    }

    /**
     * List转换
     *
     * @param sourceList 原数据数据list
     * @param clazz      目标类型
     * @param <T>
     * @return
     */
    public static <T> List<T> convertList(List sourceList, Class<T> clazz) {
        if (sourceList == null || sourceList.size() <= 0) {
            return new ArrayList<>();
        }
        List<T> resultList = new ArrayList<>();

        for (Object source : sourceList) {
            resultList.add(copyProperties(source , clazz));
        }
        return resultList;
    }
}

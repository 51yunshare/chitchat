package com.chitchat.common.annotation;

import com.chitchat.common.enumerate.EnumFunctionType;
import com.chitchat.common.enumerate.OperatorType;

import java.lang.annotation.*;

/**
 * Created by Js on 2022/7/27 .
 */
@Target({ ElementType.PARAMETER, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface OpLog {
    /**
     * 模块
     */
    public String title() default "";

    /**
     * 功能
     */
    public EnumFunctionType businessType() default EnumFunctionType.OTHER;

    /**
     * 操作人类别
     */
    public OperatorType operatorType() default OperatorType.MANAGE;
}

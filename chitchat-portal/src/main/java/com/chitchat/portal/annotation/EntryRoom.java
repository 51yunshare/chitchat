package com.chitchat.portal.annotation;


import java.lang.annotation.*;

/**
 * Created by Js 2022/9/8.
 */
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Target({ElementType.METHOD})
public @interface EntryRoom {
    String description();
    String roomId() default "id";
}

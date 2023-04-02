package com.chitchat.common.sms.service;


import com.chitchat.common.sms.enumerate.EnumMessageType;

/**
 * 发送短信
 * Created by Js on 2022/8/4 .
 *
 */
public interface Sms {

    /**
     * 发送短信消息
     * @param mobile    手机号
     * @param templateParam 模板参数
     * @param shortMessageType  短信类别
     * @return
     */
    boolean send(String mobile, EnumMessageType shortMessageType, String... templateParam);
}

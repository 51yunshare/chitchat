package com.chitchat.common.sms.bean;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 短信验证码
 *
 * Created by Js on 2022/8/12.
 **/
@Data
@Accessors(chain = true)
public class ShortMsgInfo implements Serializable {

    private String mobile;
    private String code;
    private long createdTime;
}

package com.chitchat.common.base;

import lombok.Data;

/**
 * Created by Js 2022/7/25.
 */
@Data
public class BaseUser {
    private Long id;
    private String userName;
    private String realName;
    private String token;
}

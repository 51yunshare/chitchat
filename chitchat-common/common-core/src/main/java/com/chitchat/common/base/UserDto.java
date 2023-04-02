package com.chitchat.common.base;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 登录用户信息
 * Created by Js on 2022/7/31.
 */
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class UserDto {
    private Long id;
    private String username;
    //昵称
    private String nickName;
    private String password;
    private Integer status;
    private String clientId;
    private List<String> roles;
    //国家
    private String country;
    //性别
    private Integer gender;

}

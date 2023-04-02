package com.chitchat.admin.vo.sys;

import lombok.Data;

import java.util.List;

@Data
public class UserAuthVO {

    /**
     * 用户ID
     */
    private Long id;

    /**
     * 用户名
     */
    private String username;
    /**
     * 昵称
     */
    private String nickName;

    /**
     * 用户密码
     */
    private String password;

    /**
     * 用户状态(1:正常;0:禁用)
     */
    private Integer status;

    /**
     * 用户角色编码集合 ["root","admin"]
     */
    private List<String> roles;

}

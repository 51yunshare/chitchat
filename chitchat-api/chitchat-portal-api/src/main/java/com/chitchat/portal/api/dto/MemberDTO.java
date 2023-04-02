package com.chitchat.portal.api.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * 会员传输层对象
 *
 */
@Data
@Accessors(chain = true)
public class MemberDTO implements Serializable {

    private static final long serialVersionUID = -7186995870659196109L;
    /**
     * 登录名
     */
    private String username;
    //性别
    private Integer gender;
    /**
     * 昵称
     */
    private String nickName;
    //手机号
    private String mobile;
    //密码
    private String password;
    //生日
    private Date birthday;
    //头像
    private String icon;
    //第三方唯一标识
    private String identifier;
    //第三方sessionKey
    private String sessionKey;
    //国家
    private String country;
    //城市
    private String city;
    //工作
    private String job;
    //状态
    private Integer status;
    //用户登录APP类型
    private Integer appType;

}

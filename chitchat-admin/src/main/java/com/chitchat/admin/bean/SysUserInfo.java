package com.chitchat.admin.bean;

import com.chitchat.common.base.BaseBean;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 后台用户实体
 *
 * Created by Js on 2022/8/10.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SysUserInfo extends BaseBean {


    /**
     * 用户ID
     */
    @ApiModelProperty(value = "用户id")
    private Long id;

    /**
     * 用户名
     */
    @ApiModelProperty(value = "用户登录名")
    private String username;

    /**
     * 密码
     */
    @ApiModelProperty(value = "密码")
    private String password;

    /**
     * 昵称
     */
    @ApiModelProperty(value = "用户昵称")
    private String nickName;

    @ApiModelProperty("用户性别: 0-未知 1-男 2-女")
    private Integer gender;

    @ApiModelProperty(value = "头像")
    private String avatar;

    @ApiModelProperty(value = "手机号")
    private String mobile;

    @ApiModelProperty(value = "邮箱")
    private String email;
    /**
     * 登录ip
     */
    @ApiModelProperty(value = "最后登录ip")
    private String loginIp;

    /**
     * 登录时间
     */
    @ApiModelProperty(value = "最后登录时间")
    private Date loginTime;

    /**
     * 用户状态(1:正常;0:禁用)
     */
    @ApiModelProperty(value = "用户状态(1:正常;0:禁用)")
    private Integer status;

    @ApiModelProperty(value = "创建人")
    private String creator;


}

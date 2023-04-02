package com.chitchat.admin.vo.sys;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@ApiModel(value = "用户列表信息")
public class UserBaseInfoVO implements Serializable {

    private static final long serialVersionUID = -5384619102080990425L;
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
     * 用户状态(1:正常;0:禁用)
     */
    @ApiModelProperty(value = "用户状态(1:正常;0:禁用)")
    private Integer status;

}

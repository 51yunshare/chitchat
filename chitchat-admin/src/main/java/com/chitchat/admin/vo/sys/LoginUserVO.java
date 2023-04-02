package com.chitchat.admin.vo.sys;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 登录用户视图层对象
 *
 * Created by Js on 2022/10/09.
 */
@ApiModel("当前登录用户视图对象")
@Data
public class LoginUserVO {

    @ApiModelProperty("用户ID")
    private Long id;

    @ApiModelProperty("用户昵称")
    private String nickName;

    @ApiModelProperty("头像地址")
    private String avatar;

    @ApiModelProperty("用户的角色编码集合")
    private List<String> roles;

    @ApiModelProperty("用户的按钮权限标识集合")
    private List<String> perms;

}

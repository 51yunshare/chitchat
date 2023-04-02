package com.chitchat.admin.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * Created by Js on 2022/7/31.
 **/
@Data
@Accessors(chain = true)
@ApiModel(value = "后台用户登录请求对象", description = "后台用户登录对象")
public class SysUserLoginRequest implements Serializable {
    @ApiModelProperty(value = "登录名", required = true, example = "1xxxxxxxx")
    @NotBlank(message = "登录名不能为空！")
    private String username;
    @ApiModelProperty(value = "密码", required = true, example = "***********")
    @NotBlank(message = "密码不能为空！")
    private String password;
}

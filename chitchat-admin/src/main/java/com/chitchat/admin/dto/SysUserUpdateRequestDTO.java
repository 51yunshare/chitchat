package com.chitchat.admin.dto;

import com.chitchat.common.validation.EnumValue;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * 用户新增
 *
 * Created by Js on 2022/7/31.
 **/
@Data
@ApiModel(value = "后台用户修改请求对象", description = "用户修改对象")
public class SysUserUpdateRequestDTO implements Serializable {

    private static final long serialVersionUID = 7587257253894530931L;

    @ApiModelProperty(value = "用户Id", required = true, example = "1")
    @NotNull(message = "用户Id不能为空！")
    private Long id;

    @ApiModelProperty(value = "用户名", required = true, example = "username")
    @NotBlank(message = "用户名不能为空！")
    @Size(min = 0, max = 30, message = "用户名长度不能超过30个字符")
    private String username;

    @ApiModelProperty(value = "用户昵称", required = true, example = "username")
    @NotBlank(message = "用户昵称不能为空！")
    @Size(min = 0, max = 30, message = "用户昵称长度不能超过30个字符")
    private String nickName;

    @ApiModelProperty(value = "手机号", example = "18888888888")
    @Size(min = 0, max = 11, message = "手机号码长度不能超过11个字符")
    private String mobile;

    @ApiModelProperty(value = "用户性别: 0-未知 1-男 2-女", example = "0")
    @EnumValue(intValues = {0,1,2}, message = "用户性别参数有误")
    private Integer gender;

//    @ApiModelProperty(value = "头像")
//    private String avatar;
    @ApiModelProperty(value = "角色Ids")
    private Long[] roleIds;

    @ApiModelProperty(value = "邮箱")
    @Email(message = "邮箱格式不正确")
    private String email;
    /**
     * 用户状态(1:正常;0:禁用)
     */
    @ApiModelProperty(value = "用户状态(1:正常;0:禁用)", example = "1")
    @EnumValue(intValues = {0,1}, message = "用户状态参数有误")
    private Integer status;

    @ApiModelProperty(value = "备注")
    private String memo;
}

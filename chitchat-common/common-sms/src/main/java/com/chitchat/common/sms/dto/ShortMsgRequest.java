package com.chitchat.common.sms.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * Created by Js on 2022/7/1 .
 **/
@Data
@ApiModel(value = "手机验证码校验", description = "手机验证码校验对象")
public class ShortMsgRequest implements Serializable {

    @ApiModelProperty(value = "手机号", required = true, example = "18888888888")
    @NotBlank(message = "手机号不能为空！")
    private String mobile;
    @ApiModelProperty(value = "验证码", required = true, example = "123456")
    @NotBlank(message = "验证码不能为空！")
    private String code;
}

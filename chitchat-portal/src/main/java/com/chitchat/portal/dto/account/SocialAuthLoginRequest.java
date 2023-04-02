package com.chitchat.portal.dto.account;

import com.chitchat.common.validation.EnumValue;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 第三方授权登录/注册
 *
 * Created by Js on 2022/7/31.
 **/
@Data
@ApiModel(value = "第三方授权登录/注册-请求对象", description = "第三方授权登录/注册")
public class SocialAuthLoginRequest implements Serializable {

    @ApiModelProperty(value = "第三方类型(1-Facebook 2-Google 3-Applet)", required = true, example = "2")
    @NotNull(message = "Please fill in the third party type！")
    @EnumValue(intValues = {1,2,3}, message = "Wrong third party authorization type parameter！")
    private Integer identityType;

    @ApiModelProperty(value = "第三方身份标识", required = true, example = "2022facebook258789")
    @NotBlank(message = "Third party ID cannot be empty！")
    private String identifier;

    @ApiModelProperty(value = "第三方昵称", required = true, example = "小少")
    private String nickName;
}

package com.chitchat.portal.dto.account;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 刷新Token
 *
 * Created by Js on 2022/7/31.
 **/
@Data
@Accessors(chain = true)
@ApiModel(value = "会员刷新Token", description = "会员刷新Token对象")
public class MemberRefreshTokenRequest implements Serializable {

    @ApiModelProperty(value = "刷新Token使用", required = true, example = "***********")
    @NotBlank(message = "refresh_token不能为空！")
    private String refreshToken;
}

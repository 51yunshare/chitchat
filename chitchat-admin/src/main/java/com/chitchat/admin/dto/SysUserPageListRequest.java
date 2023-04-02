package com.chitchat.admin.dto;

import com.chitchat.common.base.BasePageRequestModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by Js on 2022/8/7.
 **/
@Getter
@Setter
@ApiModel(value = "用户列表")
public class SysUserPageListRequest extends BasePageRequestModel {
//    private String nickName;
    @ApiModelProperty(value = "用户状态")
    private Integer status;
}

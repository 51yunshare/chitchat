package com.chitchat.admin.vo.sys;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel(value = "用户详情")
public class UserDetailVO extends UserBaseInfoVO {

    @ApiModelProperty(value = "备注")
    private String memo;

    @ApiModelProperty(value = "用户角色")
    private List<Integer> roles;
}

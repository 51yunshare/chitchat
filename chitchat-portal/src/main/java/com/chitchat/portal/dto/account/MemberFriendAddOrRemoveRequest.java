package com.chitchat.portal.dto.account;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 会员添加/解除好友
 *
 * Created by Js on 2022/7/31.
 **/
@Data
@ApiModel(value = "会员添加/解除好友-请求对象", description = "会员添加/解除好友")
public class MemberFriendAddOrRemoveRequest implements Serializable {


    @ApiModelProperty(value = "好友id", required = true, example = "10")
    @NotNull(message = "好友id不能为空！")
    private Long friendAccountId;
}

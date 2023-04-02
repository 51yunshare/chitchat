package com.chitchat.portal.bean.account;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;


@Data
@Accessors(chain = true)
public class AccountFriendsLink implements Serializable {

    private static final long serialVersionUID = 1801626749217357110L;

    @ApiModelProperty(value = "主键")
    private Long id;

    @ApiModelProperty(value = "账号id")
    private Long accountId;

    @ApiModelProperty(value = "好友账号id")
    private Long friendAccountId;

    @ApiModelProperty(value = "是否好友1-是 0-否")
    private Integer friendStatus;

    @ApiModelProperty(value = "取消好友时间")
    private Date cancelTime;

    @ApiModelProperty(value = "添加好友时间(二次添加)")
    private Date friendTime;

    @ApiModelProperty(value = "创建时间")
    private Date createdTime;

}

package com.chitchat.portal.bean.guild;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class GuildAccountLink implements Serializable {
    private Long id;

    @ApiModelProperty(value = "账号id")
    private Long accountId;

    @ApiModelProperty(value = "公会里昵称")
    private String agencyName;

    @ApiModelProperty(value = "公会里角色")
    private String guildRole;

    @ApiModelProperty(value = "是否删除(1-是0-否)")
    private Integer deleted;

    @ApiModelProperty(value = "加入时间")
    private Date createdTime;

    private static final long serialVersionUID = 1L;

}

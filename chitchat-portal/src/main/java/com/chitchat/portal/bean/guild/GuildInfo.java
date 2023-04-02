package com.chitchat.portal.bean.guild;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class GuildInfo implements Serializable {
    private Long id;

    @ApiModelProperty(value = "公会名称")
    private String guildName;

    @ApiModelProperty(value = "公会图标")
    private String guildIcon;

    @ApiModelProperty(value = "公会公告")
    private String guildNotice;

    @ApiModelProperty(value = "公会创建者id")
    private Long createdUserId;

    @ApiModelProperty(value = "公会创建者名称")
    private String createdUserName;

    @ApiModelProperty(value = "是否删除(1-是0-否)")
    private Integer deleted;

    @ApiModelProperty(value = "修改时间")
    private Date modifiedTime;

    @ApiModelProperty(value = "创建时间")
    private Date createdTime;

    private static final long serialVersionUID = 1L;
}

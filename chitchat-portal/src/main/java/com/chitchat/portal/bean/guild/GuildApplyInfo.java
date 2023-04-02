package com.chitchat.portal.bean.guild;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class GuildApplyInfo implements Serializable {
    private Long id;

    @ApiModelProperty(value = "类型(1-申请2-邀请)")
    private Integer type;

    @ApiModelProperty(value = "账号id")
    private Long accountId;

    @ApiModelProperty(value = "申请/邀请结果(1-同意0-拒绝)")
    private Integer applyStatus;

    @ApiModelProperty(value = "处理时间")
    private Date operateTime;

    @ApiModelProperty(value = "处理人")
    private Long operateUserId;

    @ApiModelProperty(value = "处理人名称")
    private String operateUserName;

    @ApiModelProperty(value = "申请时间")
    private Date createdTime;

    private static final long serialVersionUID = 1L;
}

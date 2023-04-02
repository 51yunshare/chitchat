package com.chitchat.admin.vo.base;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 活动详情
 *
 * Created by Js on 2022/8/17.
 **/
@Data
public class RoomTypePageVO implements Serializable {

    private static final long serialVersionUID = 5855421377613118495L;

    @ApiModelProperty(value = "主键")
    private Integer id;

    @ApiModelProperty(value = "类型名称")
    private String typeName;

    @ApiModelProperty(value = "房间人数上限")
    private Integer limitUserNum;

    @ApiModelProperty(value = "创建时间")
    private Date createdTime;
}

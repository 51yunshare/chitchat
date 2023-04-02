package com.chitchat.portal.bean.room;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class RoomPopular implements Serializable {
    private static final long serialVersionUID = 4464303400849003653L;
    @ApiModelProperty(value = "主键id")
    private Long id;

    @ApiModelProperty(value = "房间id")
    private Long roomId;

    @ApiModelProperty(value = "推荐排序")
    private Integer recommendSort;

    @ApiModelProperty(value = "创建人")
    private String creator;

    @ApiModelProperty(value = "备注")
    private String memo;

    @ApiModelProperty(value = "修改时间")
    private Date modifiedTime;

    @ApiModelProperty(value = "创建时间")
    private Date createdTime;
}

package com.chitchat.admin.api.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class RoomTypeInfoVO implements Serializable {

    private static final long serialVersionUID = -8871890246557315578L;

    @ApiModelProperty(value = "主键")
    private Integer id;

    @ApiModelProperty(value = "类型名称")
    private String typeName;

    @ApiModelProperty(value = "房间人数上限")
    private Integer limitUserNum;
}

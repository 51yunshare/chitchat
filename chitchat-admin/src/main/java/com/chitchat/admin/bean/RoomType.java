package com.chitchat.admin.bean;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

@Data
@Accessors(chain = true)
public class RoomType implements Serializable {

    private static final long serialVersionUID = 8134089550597448242L;

    @ApiModelProperty(value = "主键")
    private Integer id;

    @ApiModelProperty(value = "类型名称")
    private String typeName;

    @ApiModelProperty(value = "房间人数上限")
    private Integer limitUserNum;

    @ApiModelProperty(value = "创建时间")
    private Date createdTime;
}

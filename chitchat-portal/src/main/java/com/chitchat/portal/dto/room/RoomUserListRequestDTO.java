package com.chitchat.portal.dto.room;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 房间用户列表
 *
 * Created by Js on 2022/7/31.
 **/
@Data
@ApiModel(value = "房间用户列表对象", description = "房间用户列表对象")
public class RoomUserListRequestDTO implements Serializable {

    @ApiModelProperty(value = "房间 ID", required = true)
    @NotNull(message = "房间ID参数有误")
    private Long roomId;

    @ApiModelProperty(value = "用户登录房间的时间排序, 默认值为 0")
    //0：按时间正序
    //1：按时间倒序
    private int mode;

    @ApiModelProperty(value = "单次请求返回的用户个数，默认0~200")
    private Integer limit;

    @ApiModelProperty(value = "查询用户起始位标识，首次为空")
    private String marker;
}

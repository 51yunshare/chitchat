package com.chitchat.portal.vo.room;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 房间被禁用户
 *
 * Created by Js on 2022/8/17.
 **/
@Data
@ApiModel("房间封禁用户-响应体")
public class RoomForbiddenVO implements Serializable {

    @ApiModelProperty(value = "封禁id")
    private Long id;

    @ApiModelProperty(value = "房间id")
    private Long roomId;
    /**
     * 用户名
     */
    @ApiModelProperty(value = "用户id")
    private Long accountId;
    private String nickName;
    /**
     * 创建人
     */
    private String createdUserName;

    @ApiModelProperty(value = "封禁时间")
    private Date createdTime;
}

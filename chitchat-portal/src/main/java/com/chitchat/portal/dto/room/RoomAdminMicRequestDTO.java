package com.chitchat.portal.dto.room;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 管理员管理麦位
 *
 * Created by Js on 2022/7/31.
 **/
@Data
@ApiModel(value = "管理麦位请求对象", description = "管理员管理麦位")
public class RoomAdminMicRequestDTO implements Serializable {

    @ApiModelProperty(value = "房间Id", required = true)
    @NotNull(message = "房间id不能为空")
    private Long roomId;
    @ApiModelProperty(value = "用户id", required = true)
    @NotNull(message = "用户id不能为空")
    private Long accountId;
}

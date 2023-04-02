package com.chitchat.portal.dto.room;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 管理员换麦
 *
 * Created by Js on 2022/7/31.
 **/
@Data
@ApiModel(value = "管理员换麦请求对象", description = "管理员换麦")
public class RoomAdminSwitchMicRequestDTO implements Serializable {

    @ApiModelProperty(value = "房间Id", required = true)
    @NotNull(message = "房间id不能为空")
    private Long roomId;
    @ApiModelProperty(value = "麦位id", required = true)
    @NotNull(message = "麦位id不能为空")
    private Long micId;
}

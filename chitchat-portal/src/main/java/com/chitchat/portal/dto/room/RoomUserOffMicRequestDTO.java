package com.chitchat.portal.dto.room;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 房间用户下麦/闭麦
 * <p>
 * Created by Js on 2022/7/31.
 **/
@Data
@ApiModel(value = "房间用户下麦/闭麦请求对象", description = "用户下麦/闭麦")
public class RoomUserOffMicRequestDTO implements Serializable {

    @ApiModelProperty(value = "房间Id", required = true)
    @NotNull(message = "房间id不能为空")
    private Long roomId;

    @ApiModelProperty(value = "麦位id", required = true)
    @NotNull(message = "麦位信息不能为空")
    private Long micId;
}

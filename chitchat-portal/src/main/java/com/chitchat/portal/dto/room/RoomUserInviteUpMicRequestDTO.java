package com.chitchat.portal.dto.room;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 邀请房间用户上麦
 * <p>
 * Created by Js on 2022/7/31.
 **/
@Data
@ApiModel(value = "房间邀请用户上麦请求对象", description = "邀请用户上麦")
public class RoomUserInviteUpMicRequestDTO implements Serializable {

    @ApiModelProperty(value = "房间Id", required = true, example = "1162037")
    @NotNull(message = "房间id不能为空")
    private Long roomId;

    @ApiModelProperty(value = "用户Id", required = true, example = "116520")
    @NotNull(message = "用户Id不能为空")
    private Long accountId;

    @ApiModelProperty(value = "邀请备注", example = "快来一起玩啊")
    private String memo;
}

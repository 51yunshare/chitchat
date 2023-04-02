package com.chitchat.portal.dto.room;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 被邀请用户同意上麦
 * <p>
 * Created by Js on 2022/7/31.
 **/
@Data
@ApiModel(value = "被邀请用户同意上麦请求对象", description = "被邀请用户同意上麦")
public class RoomUserAgreeUpMicRequestDTO implements Serializable {

    @ApiModelProperty(value = "房间Id", required = true, example = "1162037")
    @NotNull(message = "房间id不能为空")
    private Long roomId;
}

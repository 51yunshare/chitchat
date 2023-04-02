package com.chitchat.portal.dto.room;

import com.chitchat.common.base.BasePageRequestModel;
import com.chitchat.common.util.EnumUtil;
import com.chitchat.portal.enumerate.EnumRoomUserType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

/**
 * 房间在线用户列表
 *
 * Created by Js on 2022/8/7.
 **/
@Getter
@Setter
@ApiModel(value = "房间在线用户列表对象")
public class RoomUserPageListRequestDTO extends BasePageRequestModel {

    @ApiModelProperty(value = "房间id", required = true)
    @NotNull(message = "房间id不能为空")
    private Long roomId;

    @ApiModelProperty(value = "用户类型(1-麦上用户 2-所有用户)")
    private Integer userType;
    @ApiModelProperty(hidden = true)
    private EnumRoomUserType enumRoomUserType;

    public void setUserType(Integer userType) {
        this.userType = userType;
        this.enumRoomUserType = userType == null ? null : EnumUtil.valueOf(EnumRoomUserType.class, userType);
    }
}

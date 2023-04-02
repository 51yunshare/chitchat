package com.chitchat.portal.dto.room;

import com.chitchat.common.util.EnumUtil;
import com.chitchat.common.validation.EnumValue;
import com.chitchat.portal.enumerate.EnumKickOutDuration;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 管理员踢人
 *
 * Created by Js on 2022/7/31.
 **/
@Data
@ApiModel(value = "踢人请求对象", description = "踢人")
public class RoomAdminKickOutRequestDTO implements Serializable {

    @ApiModelProperty(value = "房间Id", required = true)
    @NotNull(message = "房间id不能为空")
    private Long roomId;
    @ApiModelProperty(value = "用户id", required = true)
    @NotNull(message = "用户id不能为空")
    private Long accountId;

    //封禁时长
    @ApiModelProperty(value = "封禁时长(1：1小时 2：1天 3：永远)", required = true)
    @NotNull(message = "请选择封禁时长！")
    @EnumValue(intValues = {1,2,3}, message = "封禁时长参数有误！")
    private Integer kickOutDuration;
    @ApiModelProperty(hidden = true)
    private EnumKickOutDuration enumKickOutDuration;

    public void setKickOutDuration(Integer kickOutDuration) {
        this.kickOutDuration = kickOutDuration;
        this.enumKickOutDuration = kickOutDuration == null ? null : EnumUtil.valueOf(EnumKickOutDuration.class, kickOutDuration);
    }
}

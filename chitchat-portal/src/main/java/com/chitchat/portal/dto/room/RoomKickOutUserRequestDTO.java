package com.chitchat.portal.dto.room;

import com.chitchat.common.util.EnumUtil;
import com.chitchat.common.validation.EnumValue;
import com.chitchat.portal.enumerate.EnumKickOutDuration;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Set;

/**
 * 房间踢人
 * <p>
 * Created by Js on 2022/7/31.
 **/
@Data
@ApiModel(value = "房间踢人对象", description = "房间踢人")
public class RoomKickOutUserRequestDTO implements Serializable {

    @ApiModelProperty(value = "房间 ID", required = true)
    @NotNull(message = "房间ID参数有误")
    private Long roomId;

    @ApiModelProperty(value = "踢出房间的用户 ID 列表, 最大支持 5 个用户 ID", required = true)
    @NotEmpty(message = "用户参数有误")
    @Size(min = 1, max = 5, message = "单次踢人最大支持5个用户！")
    private Set<Integer> accountId;

    @ApiModelProperty(value = "踢人原因,UrlEncode")
    private String customReason;

    //封禁时长
    @ApiModelProperty(value = "封禁时长", required = true)
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

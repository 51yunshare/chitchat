package com.chitchat.portal.dto.room;

import com.chitchat.common.enumerate.EnumYesOrNo;
import com.chitchat.common.util.EnumUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 房间用户上麦
 * <p>
 * Created by Js on 2022/7/31.
 **/
@Data
@ApiModel(value = "房间用户上麦请求对象", description = "用户上麦")
public class RoomUserUpMicRequestDTO implements Serializable {

    @ApiModelProperty(value = "房间Id", required = true)
    @NotNull(message = "房间id不能为空")
    private Long roomId;
    @ApiModelProperty(value = "麦位Id", required = true)
    @NotNull(message = "麦位信息不能为空")
    private Long micId;

    @ApiModelProperty(value = "用户麦克风权限 0-关闭 1-打开")
//    @NotNull(message = "用户麦克风权限是否打开")
    private Integer accountMicStatus;
    @ApiModelProperty(hidden = true)
    private EnumYesOrNo enumMicStatus;

    public void setAccountMicStatus(Integer accountMicStatus) {
        this.accountMicStatus = accountMicStatus;
        this.enumMicStatus = accountMicStatus == null ? null : EnumUtil.valueOf(EnumYesOrNo.class, accountMicStatus);
    }
}

package com.chitchat.portal.dto.room;

import com.chitchat.common.enumerate.EnumYesOrNo;
import com.chitchat.common.validation.EnumValue;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 管理员静音操作
 *
 * Created by Js on 2022/7/31.
 **/
@Data
@ApiModel(value = "管理员锁麦/解麦请求对象", description = "管理员锁麦/解麦")
public class RoomAdminMuteMicRequestDTO implements Serializable {

    @ApiModelProperty(value = "房间Id", required = true)
    @NotNull(message = "房间id不能为空")
    private Long roomId;
    @ApiModelProperty(value = "用户id", required = true)
    @NotNull(message = "用户id不能为空")
    private Long accountId;

    @ApiModelProperty(value = "麦位静音操作类型(1-静音 0-解除静音)", required = true)
    @NotNull(message = "麦位静音操作类型不能为空")
    @EnumValue(intValues = {0,1}, message = "麦位操作参数有误")
    private Integer lockMicType;
    //麦位静音操作类型名称
    @ApiModelProperty(hidden = true)
    private String lockMicTypeName;

    public void setLockMicType(Integer lockMicType) {
        this.lockMicType = lockMicType;
        this.lockMicTypeName = lockMicType.intValue() == EnumYesOrNo.否.getIndex() ? "静音" : "解除静音";
    }
}

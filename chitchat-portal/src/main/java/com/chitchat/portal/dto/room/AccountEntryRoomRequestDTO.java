package com.chitchat.portal.dto.room;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 用户进入房间
 *
 * Created by Js on 2022/7/31.
 **/
@Data
@ApiModel(value = "用户进入房间-请求体", description = "用户进入房间对象")
public class AccountEntryRoomRequestDTO implements Serializable {

    @ApiModelProperty(value = "房间Id", required = true)
    @NotNull(message = "房间id不能为空")
    private Long roomId;

    @ApiModelProperty(value = "房间密码")
    private String roomPwd;
}

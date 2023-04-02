package com.chitchat.portal.dto.room;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 退出游戏
 * <p>
 * Created by Js on 2022/10/30 .
 **/
@Data
@ApiModel(value = "退出游戏-请求体")
public class RoomGameQuitRequestDTO implements Serializable {
    private static final long serialVersionUID = 5215564253281310520L;

    @ApiModelProperty(value = "房间号", required = true, example = "258963")
    @NotNull(message = "The room number cannot be empty !")
    private String roomNo;
}

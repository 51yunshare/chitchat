package com.chitchat.portal.dto.room;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 房间禁用户
 *
 * Created by Js on 2022/7/31.
 **/
@Data
@ApiModel(value = "房间禁用户请求对象", description = "房间禁用户")
public class RoomForbiddenAddRequestDTO implements Serializable {

    @ApiModelProperty(value = "房间id", required = true)
    @NotBlank(message = "房间id不能为空！")
    private Long roomId;

    @ApiModelProperty(value = "用户id")
    @NotBlank(message = "用户id不能为空！")
    private Long accountId;
    //封禁时长
//    @ApiModelProperty(value = "封禁时长")
//    @NotBlank(message = "请选择封禁时长！")
//    @EnumValue(intValues = {1,2,3}, message = "封禁时长参数有误！")
//    private Integer kickOutDuration;
}

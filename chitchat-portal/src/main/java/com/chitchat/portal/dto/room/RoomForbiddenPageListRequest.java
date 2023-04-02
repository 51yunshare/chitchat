package com.chitchat.portal.dto.room;

import com.chitchat.common.base.BasePageRequestModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

/**
 * 房间被禁用户列表
 *
 * Created by Js on 2022/8/7.
 **/
@Getter
@Setter
@ApiModel("房间被禁用户列表")
public class RoomForbiddenPageListRequest extends BasePageRequestModel {

    @ApiModelProperty(value = "房间id", required = true, example = "10")
    @NotNull(message = "房间id不能为空")
    private Long roomId;
}

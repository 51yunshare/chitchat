package com.chitchat.admin.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 新增房间类型
 * <p>
 * Created by Js on 2022/7/31.
 **/
@Data
@ApiModel(value = "新增房间类型请求对象", description = "新增房间类型")
public class RoomTypeAddRequestDTO implements Serializable {

    @ApiModelProperty(value = "类型名称", required = true)
    @NotBlank(message = "类型名称不能为空！")
    private String typeName;

    @ApiModelProperty(value = "房间人数上限", required = true)
    @NotNull(message = "房间人数上限不能为空！")
    private Integer limitUserNum;
}

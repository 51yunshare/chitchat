package com.chitchat.portal.dto.room;

import com.chitchat.common.validation.EnumValue;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Set;

/**
 * 销毁房间
 *
 * Created by Js on 2022/7/31.
 **/
@Data
@ApiModel(value = "销毁房间对象", description = "销毁房间对象")
public class RoomDestroyRequestDTO implements Serializable {

    @ApiModelProperty(value = "房间名称", required = true, example = "闲聊房间")
    @NotBlank(message = "请填写房间名称！")
    private String roomName;

    @ApiModelProperty(value = "房间的公告")
    private String roomNotice;

    @ApiModelProperty(value = "房间图片")
    private String roomImg;

    @ApiModelProperty(value = "房间语言")
    private String roomLanguage;

    @ApiModelProperty(value = "房间标签(数组)")
    private Set<String> roomTag;

    @ApiModelProperty(value = "连麦方式：0-随便 1-邀请")
    @EnumValue(intValues = {0, 1}, message = "连麦方式参数错误")
    private Integer micWay;
}

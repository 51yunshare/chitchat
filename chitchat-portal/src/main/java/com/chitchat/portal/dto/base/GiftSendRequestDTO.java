package com.chitchat.portal.dto.base;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 发送礼物
 * <p>
 * Created by Js on 2022/7/31.
 **/
@Data
@ApiModel(value = "发送礼物请求对象", description = "发送礼物")
public class GiftSendRequestDTO implements Serializable {


    @ApiModelProperty(value = "发送礼物对象Id", required = true)
    @NotNull(message = "发送礼物用户不能为空！")
    private Long targetId;

    @ApiModelProperty(value = "礼物id", required = true)
    @NotNull(message = "礼物id不能为空！")
    private Long id;

    @ApiModelProperty(value = "发送礼物数量", required = true)
    @NotNull(message = "发送礼物数量不能为空！")
    @Min(value = 1,message = "礼物数量最小值为1")
    private Integer giftNum;
    //房间id
    @ApiModelProperty(value = "房间id，在房间里中发送必填")
    private Long roomId;
}

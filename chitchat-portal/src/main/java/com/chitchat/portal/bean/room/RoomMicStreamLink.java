package com.chitchat.portal.bean.room;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;


@Data
@Accessors(chain = true)
@ApiModel("房间麦位流信息")
public class RoomMicStreamLink implements Serializable {
    @ApiModelProperty(value = "流id")
    private Long id;

    @ApiModelProperty(value = "房间id")
    private Long roomId;

    @ApiModelProperty(value = "麦克风id")
    private Long micId;

    @ApiModelProperty(value = "用户id")
    private Long accountId;

    @ApiModelProperty(value = "推流通道(0-主推流通道,1-第二路推流通道,2,3)")
    private Integer publishChannel;

    @ApiModelProperty(value = "码率,kbps，默认48kbps")
    private Integer audioBitrate;

    @ApiModelProperty(value = "编码器H.264*H.264、SVC*H.265")
    private String codec;

    @ApiModelProperty(value = "创建时间")
    private Date createdTime;

}

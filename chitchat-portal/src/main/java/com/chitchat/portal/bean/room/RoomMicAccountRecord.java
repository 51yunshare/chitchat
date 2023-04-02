package com.chitchat.portal.bean.room;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * 房间用户上下麦记录
 *
 */
@Data
@Accessors(chain = true)
public class RoomMicAccountRecord implements Serializable {
    private static final long serialVersionUID = -2425166268717284334L;

    @ApiModelProperty(value = "主键id")
    private Long id;

    @ApiModelProperty(value = "房间用户关联id")
    private Long roomAccountId;

    @ApiModelProperty(value = "房间id")
    private Long roomId;

    @ApiModelProperty(value = "账号id")
    private Long accountId;

    @ApiModelProperty(value = "麦位id")
    private Long micId;

    @ApiModelProperty(value = "上麦时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date onMicTime;

    @ApiModelProperty(value = "下麦操作人")
    private Long offMicOperatorId;

    @ApiModelProperty(value = "下麦操作人名称")
    private String offMicOperatorName;

    @ApiModelProperty(value = "下麦时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createdTime;

}

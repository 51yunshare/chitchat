package com.chitchat.portal.dto.room;

import com.chitchat.common.constant.SystemConstants;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Set;

/**
 * 房间自定义消息
 * <p>
 * Created by Js on 2022/7/31.
 **/
@Data
@Accessors(chain = true)
@ApiModel(value = "房间推送自定义消息对象", description = "房间推送自定义消息")
public class RoomSendCustomRequestDTO implements Serializable {

    @ApiModelProperty(value = "房间ID", required = true)
    @NotNull(message = "房间ID参数有误")
    private Long roomId;


    //发送方用户ID
//    @ApiModelProperty(value = "发送方用户ID", required = true)
//    @NotNull(message = "发送用户ID不能为空！")
    private String fromUserId = SystemConstants.SERVER_USER_ID;

    @ApiModelProperty(value = "自定义消息目的用户ID，最大支持10个目的用户ID, 如果没有此字段，自定义消息广播给房间内所有用户")
    @Size(max = 10, message = "最大支持发送10个用户")
    private Set<Long> toUserId;

    @ApiModelProperty(value = "消息内容，长度不能超过 1024 个字节，UrlEncode", required = true)
    @NotBlank(message = "消息内容不能为空！")
    @Length(min = 1, max = 1024, message = "消息内容不能超过1024个字节")
    private String messageContent;

}

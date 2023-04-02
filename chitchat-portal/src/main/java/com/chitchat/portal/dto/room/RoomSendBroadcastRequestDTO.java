package com.chitchat.portal.dto.room;

import com.chitchat.common.constant.SystemConstants;
import com.chitchat.common.util.EnumUtil;
import com.chitchat.portal.enumerate.EnumRoomMessageCategory;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 房间广播、弹幕-所有人都可以看到
 * <p>
 * Created by Js on 2022/7/31.
 **/
@Data
@ApiModel(value = "房间广播对象", description = "房间广播消息")
public class RoomSendBroadcastRequestDTO implements Serializable {

    @ApiModelProperty(value = "房间ID", required = true)
    @NotNull(message = "房间ID参数有误")
    private Long roomId;

    //发送方用户（该用户不会接收消息，无需登录房间）
//    @ApiModelProperty(value = "发送方用户ID（该用户不会接收消息，无需登录房间）", required = true)
//    @NotNull(message = "发送方用户ID不能为空！")
    private String accountId = SystemConstants.SERVER_USER_ID;
//    @ApiModelProperty(value = "发送方用户名（与 UserId 一一对应）")
    private String nickName = SystemConstants.SERVER_USER_NAME;

    @ApiModelProperty(value = "消息分类，1-系统消息，2-聊天消息，一般为聊天消息", required = true)
    @NotNull(message = "消息分类不能为空！")
    private Integer messageCategory;
    @NotNull(message = "消息分类参数有误")
    private EnumRoomMessageCategory enumRoomMessageCategory;

    @ApiModelProperty(value = "消息内容，长度不能超过 1024 个字节，UrlEncode", required = true)
    @NotBlank(message = "消息内容不能为空！")
    @Length(min = 1, max = 1024, message = "消息内容不能超过1024个字节")
    private String messageContent;

    public void setMessageCategory(Integer messageCategory) {
        this.messageCategory = messageCategory;
        this.enumRoomMessageCategory = messageCategory == null ? null : EnumUtil.valueOf(EnumRoomMessageCategory.class, messageCategory);
    }
}

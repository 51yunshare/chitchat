package com.chitchat.portal.dto.room;

import com.chitchat.common.constant.SystemConstants;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 增加房间流
 * <p>
 * Created by Js on 2022/7/31.
 **/
@Data
@ApiModel(value = "增加房间流对象", description = "增加房间流")
public class RoomAddStreamRequestDTO implements Serializable {

    @ApiModelProperty(value = "房间ID", required = true)
    @NotNull(message = "房间ID参数有误")
    private Long roomId;

//    @ApiModelProperty(value = "用户ID, 不建议使用与房间内实际用户相同的 UserId", required = true)
//    @NotNull(message = "用户ID不能为空！")
    private String accountId = SystemConstants.SERVER_USER_ID;
//    @ApiModelProperty(value = "用户名")
    private String nickName = SystemConstants.SERVER_USER_NAME;

    @ApiModelProperty(value = "流ID", required = true)
    @NotNull(message = "流ID不能为空！")
    private Integer streamId;
    @ApiModelProperty(value = "流标题，不超过 127 字节，进行 UrlEncode")
    private String streamTitle;

    @ApiModelProperty(value = "流附加信息，不超过 1024 字节")
    private String extraInfo;

}

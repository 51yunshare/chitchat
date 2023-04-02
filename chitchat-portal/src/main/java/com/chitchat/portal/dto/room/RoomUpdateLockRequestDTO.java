package com.chitchat.portal.dto.room;

import com.chitchat.common.validation.EnumValue;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 房间编辑-是否上锁
 *
 * Created by Js on 2022/7/31.
 **/
@Data
@ApiModel(value = "房间是否上锁请求对象", description = "房间是否上锁对象")
public class RoomUpdateLockRequestDTO implements Serializable {

    @ApiModelProperty(value = "房间Id", required = true)
    @NotNull(message = "房间id不能为空")
    private Long id;

    @ApiModelProperty(value = "是否上锁：0-否 1-是")
    @EnumValue(intValues = {0, 1}, message = "上锁参数错误")
    private Integer whetherLock;

    @ApiModelProperty(value = "房间密码(上锁必填密码-6位数字)")
//    @Pattern(regexp = "[0123456789]", message = "房间密码为6位数字")
//    @Size(max = 6, min = 6, message = "房间密码为6位数字")
    private String roomPwd;
}

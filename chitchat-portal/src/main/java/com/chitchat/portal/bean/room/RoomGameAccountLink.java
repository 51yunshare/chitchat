package com.chitchat.portal.bean.room;

import com.chitchat.common.base.BaseBean;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoomGameAccountLink extends BaseBean {

    private Long id;

    @ApiModelProperty(value = "房间id")
    private Long roomId;

    @ApiModelProperty(value = "房间号")
    private String roomNo;

    @ApiModelProperty(value = "用户id")
    private Long accountId;

    @ApiModelProperty(value = "用户昵称")
    private String nickName;

    @ApiModelProperty(value = "用户角色(1：主播 2：观众 3：管理员)")
    private Integer userRole;

    @ApiModelProperty(value = "用户登录房间时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date loginTime;

    @ApiModelProperty(value = "退出房间时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date logoutTime;
}

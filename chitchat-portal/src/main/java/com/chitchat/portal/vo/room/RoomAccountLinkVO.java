package com.chitchat.portal.vo.room;

import com.chitchat.common.util.EnumUtil;
import com.chitchat.portal.enumerate.EnumRoomUserRole;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 房间用户信息
 *
 * Created by Js on 2022/8/3 .
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("房间在线用户列表信息")
public class RoomAccountLinkVO implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     *
     */
//    @ApiModelProperty(value = "关联id")
//    private Long id;
    @ApiModelProperty(value = "房间id")
    private Long roomId;
    /**
     * 用户id
     */
    @ApiModelProperty(value = "用户id")
    private Long accountId;
    /**
     * 用户名
     */
    @ApiModelProperty(value = "用户昵称")
    private String nickName;
    //头像
    @ApiModelProperty(value = "用户头像")
    private String icon;
    @ApiModelProperty(value = "用户性别")
    //性别
    private Integer gender;
    //国家
    @ApiModelProperty(value = "用户国家")
    private String country;


    /**
     * 用户角色
     */
    @ApiModelProperty(value = "用户角色id(1-主播 2-观众 3-管理员)")
    private Integer userRole;
    @ApiModelProperty(value = "用户角色名称")
    private EnumRoomUserRole enumRoomUserRole;

    @ApiModelProperty(value = "用户等级")
    private Long accountLevelId;
    /**
     * 游戏
     */
    @ApiModelProperty(value = "游戏等级")
    private Long accountGameLevelId;
    /**
     * VIP等级
     */
    @ApiModelProperty(value = "VIP等级")
    private Long vipLevelId;
    /**
     * 贵族等级
     */
    @ApiModelProperty(value = "贵族等级")
    private Long kingLevelId;

    /**
     * 登录房间时间
     */
    @ApiModelProperty(value = "最新登录时间")
    private Date loginTime;

    public void setUserRole(Integer userRole) {
        this.userRole = userRole;
        this.enumRoomUserRole = userRole == null ? null : EnumUtil.valueOf(EnumRoomUserRole.class, userRole);
    }

    private Date createdTime;
}

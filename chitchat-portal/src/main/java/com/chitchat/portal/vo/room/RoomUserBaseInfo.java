package com.chitchat.portal.vo.room;

import com.chitchat.common.util.EnumUtil;
import com.chitchat.portal.enumerate.EnumRoomUserRole;
import com.chitchat.portal.vo.account.AccountBaseInfoVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "房间用户基本信息")
public class RoomUserBaseInfo extends AccountBaseInfoVO {

    /*private static final long serialVersionUID = 5533291645771711139L;

    @ApiModelProperty("用户id")
    private Long id;
    //昵称
    @ApiModelProperty("用户昵称")
    private String nickName;
    //头像
    @ApiModelProperty("用户icon")
    private String icon;
    //性别
    @ApiModelProperty("用户性别: 0-未知 1-男 2-女")
    private Integer gender;

    @ApiModelProperty("用户国家")
    private String country;

    @ApiModelProperty(value = "用户等级")
    private Long accountLevelId;
    *//**
     * 游戏
     *//*
    @ApiModelProperty(value = "游戏等级")
    private Long accountGameLevelId;
    *//**
     * VIP等级
     *//*
    @ApiModelProperty(value = "VIP等级")
    private Long vipLevelId;
    *//**
     * 贵族等级
     *//*
    @ApiModelProperty(value = "贵族等级")
    private Long kingLevelId;*/

    /**
     * 用户角色
     */
    @ApiModelProperty(value = "用户角色id(1-主播 2-观众 3-管理员)")
    private Integer userRole;
    @ApiModelProperty(value = "用户角色名称")
    private EnumRoomUserRole enumRoomUserRole;

    //房间收到礼物价值
    @ApiModelProperty(value = "用户房间接收贡献值")
    private Long roomReceivingContributionNum;
    //房间贡献值
    @ApiModelProperty(value = "用户房间贡献值")
    private Long roomContributionNum;

    public void setUserRole(Integer userRole) {
        this.userRole = userRole;
        this.enumRoomUserRole = userRole == null ? null : EnumUtil.valueOf(EnumRoomUserRole.class, userRole);
    }

    @ApiModelProperty(value = "关注状态")
    private Integer followStatus;

    @ApiModelProperty(value = "上麦状态")
    private Integer upMicStatus;

}

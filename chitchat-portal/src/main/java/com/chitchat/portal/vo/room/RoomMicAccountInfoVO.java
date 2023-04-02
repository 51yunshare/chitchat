package com.chitchat.portal.vo.room;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 房间麦位用户详情
 *
 * Created by Js on 2022/8/17.
 **/
@Data
@ApiModel("房间麦位用户信息")
public class RoomMicAccountInfoVO implements Serializable {


    /*** 麦位信息 ***/
    //麦位id
    @ApiModelProperty(value = "麦位id")
    private Long id;
    /**
     * 麦位序号
     */
    @ApiModelProperty(value = "麦位序号")
    private Integer orderNum;
    /**
     * 麦克风状态(0-空闲1-非空闲)
     *
     */
    @ApiModelProperty(value = "麦位状态(0-空闲1-非空闲)")
    private Integer micStatus;
    /**
     * 麦位是否锁定
     */
    @ApiModelProperty(value = "麦位是否锁定")
    private Integer whetherLock;



    /*** 麦位用户信息 ***/
    //用户id
    @ApiModelProperty(value = "用户id")
    private Long accountId;
    //昵称
    @ApiModelProperty(value = "昵称")
    private String nickName;
    //头像
    @ApiModelProperty(value = "头像")
    private String icon;
    //性别
    @ApiModelProperty(value = "性别")
    private Integer gender;

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
     * 国家
     */
    @ApiModelProperty(value = "国家")
    private String country;
    //用户麦克风状态：打开还是关闭
    @ApiModelProperty(value = "用户麦克风状态：1-打开 0-关闭")
    private Integer accountMicStatus;
    //麦位是否静音(管理员操作)
    @ApiModelProperty(value = "麦位是否静音 0-否 1-是")
    private Integer muteMicStatus;
}

package com.chitchat.portal.vo.room;

import com.alibaba.fastjson.JSONArray;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 我的房间
 *
 * Created by Js on 2022/8/17.
 **/
@Data
@ApiModel("首页房间列表信息")
public class MyRoomPageListVO implements Serializable {

    @ApiModelProperty("房间id")
    private Long id;

    /**
     * 房间名称
     */
    @ApiModelProperty("房间名称")
    private String roomName;
    /**
     * 房间图片
     */
    @ApiModelProperty("房间图片")
    private String roomImg;

    /**
     * 房间语言
     */
    @ApiModelProperty("房间语言")
    private String roomLanguage;
    /**
     * 国家
     */
    @ApiModelProperty("国家")
    private String country;
    @ApiModelProperty(hidden = true)
    private String roomCountry;

    /**
     * 房间公告
     */
    @ApiModelProperty("房间公告")
    private String roomNotice;
    /**
     * 房间标签
     *
     */
    @ApiModelProperty("房间标签")
    private JSONArray roomTag;
    /**
     * 连麦方式：0-随便 1-邀请
     *
     */
    @ApiModelProperty("连麦方式：0-随便 1-邀请")
    private Integer micWay;
    /**
     * 房间人数
     */
    @ApiModelProperty("房间在线人数")
    private Integer roomUserNum;
    /**
     * 房间上限人数
     */
    @ApiModelProperty(value = "房间上限人数")
    private Integer limitUserNum;
    /**
     * 热度值
     */
    @ApiModelProperty("热度值")
    private Integer hotNum;

    public void setRoomCountry(String roomCountry) {
        this.roomCountry = roomCountry;
        this.country = roomCountry;
    }
    /**
     * 房间是否锁定
     */
    @ApiModelProperty("房间是否上锁（0-否 1-是）")
    private Integer whetherLock;
}

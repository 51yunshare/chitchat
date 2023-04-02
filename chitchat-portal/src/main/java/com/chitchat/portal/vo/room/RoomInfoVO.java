package com.chitchat.portal.vo.room;

import com.alibaba.fastjson.JSONArray;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 创建房间-响应体
 *
 * Created by Js on 2022/8/17.
 **/
@Data
@ApiModel("创建房间-响应体")
public class RoomInfoVO implements Serializable {

    @ApiModelProperty(value = "房间id")
    private Long id;

    @ApiModelProperty(value = "房间名称")
    private String roomName;
    /**
     * 房间图片
     */
    @ApiModelProperty(value = "房间图片")
    private String roomImg;
    /**
     * 公告
     */
    @ApiModelProperty(value = "房间公告")
    private String roomNotice;
    /**
     * 房间标签
     *
     */
    @ApiModelProperty(value = "房间标签")
    private JSONArray roomTag;
    /**
     * 房间语言
     */
    @ApiModelProperty(value = "房间语言")
    private String roomLanguage;
    /**
     * 房间国家(房主国家)
     */
    @ApiModelProperty(value = "房间国家")
    private String roomCountry;
    /**
     * 连麦方式：0-随便 1-邀请
     *
     */
    @ApiModelProperty(value = "连麦方式：0-随便 1-邀请")
    private Integer micWay;
    /**
     * 房间人数
     */
    @ApiModelProperty(value = "房间在线人数")
    private Integer roomUserNum;
    /**
     * 房间上限人数
     */
    @ApiModelProperty(value = "房间上限人数")
    private Integer limitUserNum;
    /**
     * 房间麦克风数量(应该有个默认值)
     */
    @ApiModelProperty(value = "房间麦克风数量(默认值-9)")
    private Integer micNum;
    /**
     * 房间是否锁定
     */
    @ApiModelProperty(value = "房间是否锁定(1-是 0-否)")
    private Integer whetherLock;

    @ApiModelProperty(value = "房间创建时间")
    private Date createdTime;
}

package com.chitchat.portal.vo.room;

import com.alibaba.fastjson.JSONArray;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 进入房间信息
 *
 * Created by Js on 2022/10/14 .
 **/
@Data
@ApiModel(value = "进入房间-返回体")
public class EntryRoomInfoVO implements Serializable {
    private static final long serialVersionUID = 1289943828339226863L;

    /******** 房间信息 *********/

    @ApiModelProperty("房间Id")
    private Long id;
    @ApiModelProperty("房间名称")
    private String roomName;
    /**
     * 房间图片
     */
    @ApiModelProperty("房间封面")
    private String roomImg;
    /**
     * 公告
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
     * 房间语言
     */
    @ApiModelProperty("房间语言")
    private String roomLanguage;
    /**
     * 房间国家(房主国家)
     */
    @ApiModelProperty("房间国家")
    private String roomCountry;
    /**
     * 连麦方式
     *
     */
    @ApiModelProperty("房间连麦方式：0-随便 1-邀请")
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
    @ApiModelProperty("房间麦位数量")
    private Integer micNum;

    @ApiModelProperty("房间用户基本信息")
    private RoomUserBaseInfo roomUserInfo;
}

package com.chitchat.portal.vo.room;

import com.alibaba.fastjson.JSONArray;
import com.chitchat.portal.vo.account.AccountBaseInfoVO;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 房间详情
 *
 * Created by Js on 2022/8/17.
 **/
@Data
@ApiModel("房间详情-响应体")
public class RoomDetailVO implements Serializable {

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
    /**
     * 房间是否锁定
     */
    @ApiModelProperty("房间是否锁定")
    private Integer whetherLock;

    @ApiModelProperty("房间创建时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createdTime;

    //房主信息
    @ApiModelProperty("房主信息")
    private AccountBaseInfoVO roomOwner;
    //麦位信息+用户在麦信息
    @ApiModelProperty("房间麦位信息+用户在麦信息")
    private List<RoomMicAccountInfoVO> micAccountInfo;
    @ApiModelProperty("房间主麦位信息+用户信息")
    private RoomMicAccountInfoVO mainMicInfo;

    @ApiModelProperty("房间送礼总值")
    private Long contributionNum;

    @ApiModelProperty("房间热度值")
    private Long hotNum;
}

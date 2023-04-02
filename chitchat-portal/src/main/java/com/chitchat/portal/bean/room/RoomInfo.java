package com.chitchat.portal.bean.room;

import com.alibaba.fastjson.JSONArray;
import com.chitchat.common.base.BaseBean;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by Js on 2022/8/3 .
 **/
@Data
@Accessors(chain = true)
public class RoomInfo extends BaseBean {
    private static final long serialVersionUID = 1L;
    /**
     *
     */
    private Long id;

    /**
     * 房间名称
     */
    private String roomName;
    /**
     * 房间类型id
     */
    private Integer roomType;
    /**
     * 房间类型名称
     */
    private String roomTypeName;
    /**
     * 房间上限人数
     */
    private Integer limitUserNum;
    /**
     * 房间图片
     */
    private String roomImg;

    /**
     * 房间生命周期唯一标识
     */
    private String roomSeq;

    /**
     * 房间用户列表变更 seq，用户登录或者退出都会递增 1
     */
    private Integer userUpdateSeq;

    /**
     * 房间语言
     */
    private String roomLanguage;
    /**
     * 房间国家(房主国家)
     */
    private String roomCountry;
    private String country;
    /**
     * 房间标签
     *
     */
    private JSONArray roomTag;

    /**
     * 房间公告
     */
    private String roomNotice;
    /**
     * 连麦方式：0-随便 1-邀请
     *
     */
    private Integer micWay;
    /**
     * 房间在线人数
     */
    private Integer roomUserNum;
    /**
     * 房间麦克风数量(应该有个默认值)
     */
    private Integer micNum;
    /**
     * 热度值
     *
     */
    private Integer hotNum;
    /**
     * 贡献值
     */
    private BigDecimal contributionNum;
    /**
     * 房间是否锁定
     */
    private Integer whetherLock;
    /**
     * 房间密码
     */
    private String roomPwd;
    /**
     * 房间销毁时间
     */
    private Date destroyTime;

    public void setRoomCountry(String roomCountry){
        this.roomCountry = roomCountry;
        this.country = roomCountry;
    }
}

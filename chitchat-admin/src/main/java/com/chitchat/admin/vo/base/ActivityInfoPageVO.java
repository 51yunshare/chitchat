package com.chitchat.admin.vo.base;

import com.chitchat.admin.enumerate.EnumActivityType;
import com.chitchat.common.util.EnumUtil;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 活动详情
 *
 * Created by Js on 2022/8/17.
 **/
@Data
public class ActivityInfoPageVO implements Serializable {

    private static final long serialVersionUID = -326843872762109809L;

    private Long id;
    /**
     * 活动类型
     */
    private Integer type;
    private EnumActivityType enumActivityType;
    /**
     * 活动标题
     */
    private String title;
    /**
     * 活动图片
     */
    private String picUrl;

    /**
     * 活动连接
     */
    private String activityUrl;

    /**
     * 活动内容
     */
    private String activityContent;
    /**
     * 开始时间
     */
    private Date startTime;
    /**
     * 结束时间
     */
    private Date endTime;
    /**
     * 顺序
     */
    private Integer sort;
    /**
     * 是否开启
     */
    private Integer startStatus;

    private Date createdTime;
    private String memo;

    public void setType(Integer type) {
        this.type = type;
        this.enumActivityType = type == null ? null : EnumUtil.valueOf(EnumActivityType.class, type);
    }
}

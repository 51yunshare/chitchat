package com.chitchat.portal.bean.base;

import com.chitchat.common.base.BaseBean;
import lombok.Data;

/**
 * 活动信息
 *
 * Created by Js on 2022/8/27 .
 */
@Data
public class ActivityInfo extends BaseBean{

    private static final long serialVersionUID = 1L;
    /**
     *
     */
    private Long id;
    /**
     * 活动类型
     */
    private Long type;
    /**
     * 活动标题
     */
    private String title;
    /**
     * 活动图片
     */
    private String activityImg;

    /**
     * 活动连接
     */
    private String activityUrl;

    /**
     * 活动内容
     */
    private String activityContent;
    /**
     * 顺序
     */
    private Integer sort;
}

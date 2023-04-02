package com.chitchat.portal.bean.base;

import com.alibaba.fastjson.JSONArray;
import com.chitchat.common.base.BaseBean;
import lombok.Data;

import java.io.Serializable;

/**
 * 反馈信息
 *
 * Created by Js on 2022/8/27 .
 */
@Data
public class FeedbackInfo extends BaseBean implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     *
     */
    private Long id;

    /**
     * 反馈类型(Help、Bug、Suggestions)
     */
    private Integer feedbackType;

    /**
     * 反馈内容
     */
    private String feedbackContent;

    /**
     * 附件、图片
     */
    private JSONArray feedbackFiles;

    /**
     * 联系方式(WhatsApp、Email、Phone)
     */
    private Integer contactType;

    /**
     * 联系方式
     */
    private String contactValue;
    /**
     * 反馈用户
     */
    private Long accountId;
    private String nickName;
}

package com.chitchat.portal.dto.base;

import com.alibaba.fastjson.JSONArray;
import com.chitchat.common.util.EnumUtil;
import com.chitchat.common.validation.EnumValue;
import com.chitchat.portal.enumerate.EnumContactType;
import com.chitchat.portal.enumerate.EnumFeedbackType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 用户反馈
 * <p>
 * Created by Js on 2022/7/31.
 **/
@Data
@ApiModel(value = "用户反馈对象", description = "用户反馈")
public class FeedbackAddRequestDTO implements Serializable {

    @ApiModelProperty(value = "反馈类型，1-Help 2-Bug 3-Suggestions", required = true, allowableValues = "range[1,2,3]")
    @NotNull(message = "反馈类型不能为空！")
    @EnumValue(intValues = {1, 2, 3}, message = "反馈类型参数有误！")
    private Integer feedbackType;
    private EnumFeedbackType enumFeedbackType;

    @ApiModelProperty(value = "反馈内容", required = true)
    @NotBlank(message = "反馈内容不能为空！")
    private String feedbackContent;

    @ApiModelProperty(value = "截图附件")
    private JSONArray feedbackFiles;
    /**
     * 联系方式
     */
    @ApiModelProperty(value = "联系方式, 1-WhatsApp 2-Email 3-Phone", required = true, allowableValues = "range[1,2,3]")
    @NotNull(message = "联系方式不能为空！")
    @EnumValue(intValues = {1, 2, 3}, message = "联系方式参数有误！")
    private Integer contactType;
    private EnumContactType enumContactType;

    @ApiModelProperty(value = "联系方式具体内容", required = true)
    @NotBlank(message = "反馈内容不能为空！")
    private String contactValue;

    public void setFeedbackType(Integer feedbackType) {
        this.feedbackType = feedbackType;
        this.enumFeedbackType = feedbackType == null ? null : EnumUtil.valueOf(EnumFeedbackType.class, feedbackType);
    }

    public void setContactType(Integer contactType) {
        this.contactType = contactType;
        this.enumContactType = contactType == null ? null : EnumUtil.valueOf(EnumContactType.class, contactType);
    }
}

package com.chitchat.admin.dto;

import com.chitchat.admin.enumerate.EnumActivityType;
import com.chitchat.common.util.EnumUtil;
import com.chitchat.common.validation.EnumValue;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * 新增活动
 * <p>
 * Created by Js on 2022/7/31.
 **/
@Data
@ApiModel(value = "新增活动请求对象", description = "新增活动")
public class ActivityInfoAddRequestDTO implements Serializable {

    @ApiModelProperty(value = "活动类型(1-首页Banner 2-其他)", required = true)
    @NotNull(message = "活动类型不能为空！")
    private Integer type;
    private EnumActivityType enumActivityType;

    @ApiModelProperty(value = "活动标题", required = true)
    @NotBlank(message = "活动标题不能为空！")
    private String title;

    @ApiModelProperty(value = "活动图片")
    private MultipartFile pic;
    //活动地址
    @ApiModelProperty(value = "活动地址")
    private String activityUrl;
    @ApiModelProperty(value = "活动内容")
    private String activityContent;
    @ApiModelProperty(value = "开始时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;
    @ApiModelProperty(value = "结束时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;
    @ApiModelProperty(value = "顺序")
    private Integer sort;
    @ApiModelProperty(value = "是否开启(1:开启 0:关闭)")
    @EnumValue(intValues = {0,1}, message = "是否开启参数有误")
    private Integer startStatus;
    //备注
    private String memo;

    public void setType(Integer type) {
        this.type = type;
        this.enumActivityType = type == null ? null : EnumUtil.valueOf(EnumActivityType.class, type);
    }
}

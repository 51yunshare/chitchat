package com.chitchat.admin.dto;

import com.chitchat.common.base.BasePageRequestModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * 活动列表
 *
 * Created by Js on 2022/8/7.
 **/
@Getter
@Setter
@ApiModel
@Accessors(chain = true)
public class ActivityInfoPageListRequestDTO extends BasePageRequestModel {

    @ApiModelProperty(value = "活动类型(1-首页Banner 2-其他)", notes = "活动类型", example = "1")
    private Integer type;
}

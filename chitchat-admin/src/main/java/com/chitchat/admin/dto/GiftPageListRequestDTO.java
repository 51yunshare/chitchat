package com.chitchat.admin.dto;

import com.chitchat.common.base.BasePageRequestModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 礼物列表
 *
 * Created by Js on 2022/8/7.
 **/
@Getter
@Setter
@ApiModel
public class GiftPageListRequestDTO extends BasePageRequestModel {

    @ApiModelProperty(value = "礼物类型", notes = "", example = "1")
    private Long giftType;
}

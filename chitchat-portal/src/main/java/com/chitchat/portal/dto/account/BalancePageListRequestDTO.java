package com.chitchat.portal.dto.account;

import com.chitchat.common.base.BasePageRequestModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 余额列表
 *
 * Created by Js on 2022/8/7.
 **/
@Data
@Accessors(chain = true)
@ApiModel(value = "余额列表请求体")
public class BalancePageListRequestDTO extends BasePageRequestModel {
    //余额类型（1-金币 2-钻石）
    @ApiModelProperty(value = "余额类型（1-金币 2-钻石）", example = "1", hidden = true)
    private Integer type;

}

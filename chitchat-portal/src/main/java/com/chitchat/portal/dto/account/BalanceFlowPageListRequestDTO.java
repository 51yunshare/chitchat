package com.chitchat.portal.dto.account;

import com.chitchat.common.base.BasePageRequestModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 流水列表
 *
 * Created by Js on 2022/8/7.
 **/
@Data
@Accessors(chain = true)
@ApiModel(value = "余额流水列表请求体")
public class BalanceFlowPageListRequestDTO extends BasePageRequestModel {
    //余额类型（1-金币 2-钻石）
    @ApiModelProperty(value = "余额类型（1-金币 2-钻石）", example = "1", hidden = true)
    private Integer type = 1;
    //流水类型(0->充值；1->消费；2-送礼)
    @ApiModelProperty(value = "流水类型(100->充值；200->送礼；300-收礼)")
    private Integer flowType;

}

package com.chitchat.portal.vo.account;

import com.chitchat.common.util.EnumUtil;
import com.chitchat.portal.api.enumerate.EnumBalanceFlowType;
import com.chitchat.portal.api.enumerate.EnumBalanceType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 流水记录
 *
 * Created by Js on 2022/9/22 .
 **/
@Data
@ApiModel(value = "流水记录-响应体")
public class BalanceFlowInfoListVO implements Serializable {

    @ApiModelProperty(value = "流水id")
    private Long id;

    @ApiModelProperty(value = "操作余额类型id(1-金币 2-钻石)")
    private Integer type;
    @ApiModelProperty(value = "操作余额类型")
    private EnumBalanceType enumBalanceType;

    @ApiModelProperty(value = "流水类型id(100->充值；200->送礼；300-收礼)")
    private Integer flowType;
    @ApiModelProperty(value = "流水类型")
    private EnumBalanceFlowType balanceFlowType;

    @ApiModelProperty(value = "操作数量")
    private BigDecimal curOrderNum;

    @ApiModelProperty(value = "操作时间")
    private Date createdTime;

    public void setType(Integer type) {
        this.type = type;
        this.enumBalanceType = type == null ? null : EnumUtil.valueOf(EnumBalanceType.class , type);
    }

    public void setFlowType(Integer flowType) {
        this.flowType = flowType;
        this.balanceFlowType = flowType == null ? null : EnumUtil.valueOf(EnumBalanceFlowType.class , flowType);
    }
}

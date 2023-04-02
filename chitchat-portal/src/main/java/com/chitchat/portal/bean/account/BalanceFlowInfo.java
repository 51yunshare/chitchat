package com.chitchat.portal.bean.account;

import com.chitchat.common.base.BaseBean;
import com.chitchat.common.util.EnumUtil;
import com.chitchat.portal.api.enumerate.EnumBalanceFlowType;
import com.chitchat.portal.api.enumerate.EnumBalanceType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("用户余额流水记录")
public class BalanceFlowInfo extends BaseBean {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "流水id")
    private Long id;

    @ApiModelProperty(value = "操作余额类型id(1-金币 2-钻石)")
    private Integer type;
    @ApiModelProperty(value = "操作余额类型")
    private EnumBalanceType enumBalanceType;

    @ApiModelProperty(value = "流水类型id(100->充值；200->送礼；201-收礼)")
    private Integer flowType;
    @ApiModelProperty(value = "流水类型")
    private EnumBalanceFlowType balanceFlowType;

    @ApiModelProperty(value = "操作前余额总数")
    private BigDecimal preBalanceNum;

    @ApiModelProperty(value = "操作数量")
    private BigDecimal curOrderNum;

    @ApiModelProperty(value = "操作后余额总数")
    private BigDecimal postBalanceNum;

    @ApiModelProperty(value = "充值流水id")
    private Long payId;

    @ApiModelProperty(value = "订单id")
    private Long orderId;
    @ApiModelProperty(value = "订单编号")
    private String orderSn;

    @ApiModelProperty(value = "操作账号id")
    private Long accountId;

    @ApiModelProperty(value = "账号余额id")
    private Long balanceId;

    public void setType(Integer type) {
        this.type = type;
        this.enumBalanceType = type == null ? null : EnumUtil.valueOf(EnumBalanceType.class , type);
    }

    public void setFlowType(Integer flowType) {
        this.flowType = flowType;
        this.balanceFlowType = flowType == null ? null : EnumUtil.valueOf(EnumBalanceFlowType.class , flowType);
    }
}

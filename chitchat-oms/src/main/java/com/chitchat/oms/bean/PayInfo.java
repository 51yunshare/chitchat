package com.chitchat.oms.bean;

import com.chitchat.common.base.BaseBean;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class PayInfo extends BaseBean {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键id")
    private Long id;

    @ApiModelProperty(value = "支付流水号")
    private String paySn;

    @ApiModelProperty(value = "订单id")
    private Long orderId;
    @ApiModelProperty(value = "订单号")
    private String orderSn;

    @ApiModelProperty(value = "支付金额")
    private String payAmount;

    @ApiModelProperty(value = "支付时间")
    private Date payTime;

    @ApiModelProperty(value = "支付方式【1->支付宝；2->微信；3->银联； 4->货到付款；】")
    private Integer payType;

    @ApiModelProperty(value = "支付状态")
    private Integer payStatus;

    @ApiModelProperty(value = "确认时间")
    private Date confirmTime;

    @ApiModelProperty(value = "回调内容")
    private String callbackContent;

    @ApiModelProperty(value = "回调时间")
    private Date callbackTime;

    @ApiModelProperty(value = "交易内容")
    private String paySubject;
}

package com.chitchat.oms.bean;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 订单信息表
 * <p>
 * Created by Js on 2022/9/13.
 **/
@Data
@Accessors(chain = true)
public class OrderInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "订单id")
    private Long id;

    @ApiModelProperty(value = "订单编号")
    private String orderSn;

    @ApiModelProperty(value = "用户id")
    private Long accountId;

    @ApiModelProperty(value = "订单总金额")
    private BigDecimal totalAmount;

    @ApiModelProperty(value = "订单商品总数")
    private Integer totalNum;

    @ApiModelProperty(value = "订单状态：101->待付款；102->用户取消；103->系统取消；201->已付款；202->申请退款；203->已退款；" +
            "301->待发货；401->已发货；501->用户收货；502->系统收货；901->已完成；")
    private Integer orderStatus;

    @ApiModelProperty(value = "订单来源：0->PC订单；1->app订单")
    private Integer sourceType;


    @ApiModelProperty(value = "应付金额（实际支付金额）")
    private BigDecimal payAmount;

    @ApiModelProperty(value = "支付方式【1->微信jsapi；2->支付宝；3->余额； 4->微信app；】")
    private Integer payType;

    @ApiModelProperty(value = "支付时间")
    private Date paymentTime;

    @ApiModelProperty(value = "微信支付等第三方支付平台的商户订单号")
    private String outTradeNo;

    @ApiModelProperty(value = "微信支付订单号")
    private String transactionId;

    @ApiModelProperty(value = "商户退款单号")
    private String outRefundNo;

    @ApiModelProperty(value = "微信退款单号")
    private String refundId;

    @ApiModelProperty(value = "订单备注")
    private String memo;

    @ApiModelProperty(value = "发货时间")
    private Date deliveryTime;

    @ApiModelProperty(value = "确认收货时间")
    private Date receiveTime;

    @ApiModelProperty(value = "删除状态：0->未删除；1->已删除")
    private Integer deleted;

    @ApiModelProperty(value = "修改时间")
    private Date modifiedTime;

    @ApiModelProperty(value = "提交时间")
    private Date createTime;
}

package com.chitchat.oms.bean;

import com.chitchat.common.base.BaseBean;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 订单明细记录
 */
@Data
@Builder
public class OrderDetail extends BaseBean {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键id")
    private Long id;

    @ApiModelProperty(value = "订单id")
    private Long orderId;

    @ApiModelProperty(value = "订单编号")
    private String orderSn;

    @ApiModelProperty(value = "商品id")
    private Long goodsId;

    @ApiModelProperty(value = "图片")
    private String goodsCover;

    @ApiModelProperty(value = "商品名称")
    private String goodsName;

    @ApiModelProperty(value = "销售价格")
    private BigDecimal goodsPrice;

    @ApiModelProperty(value = "购买数量")
    private Integer goodsNum;

    @ApiModelProperty(value = "总金额")
    private BigDecimal totalAmount;

    @ApiModelProperty(value = "商品分类id")
    private Long goodsCategoryId;

    @ApiModelProperty(value = "商品分类名称")
    private String goodsCategoryName;

}

package com.chitchat.oms.dto;

import com.chitchat.common.util.EnumUtil;
import com.chitchat.common.validation.EnumValue;
import com.chitchat.portal.api.enumerate.EnumBackpackGoodsSource;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 生成订单
 *
 * Created by Js on 2022/7/31.
 **/
@Data
@ApiModel(value = "生成订单请求对象", description = "生成订单")
public class OrderInfoAddRequestDTO implements Serializable {

    @ApiModelProperty(value = "商品id", required = true)
    @NotBlank(message = "商品id不能为空！")
    private Long goodsId;
    /**
     * 操作类型(1-购买2-赠送)
     */
    @ApiModelProperty(value = "操作类型(1-购买2-赠送)", required = true, example = "1")
    @NotBlank(message = "操作类型不能为空！")
    private Integer operType;
    @ApiModelProperty(hidden = true)
    @EnumValue(intValues = {1,2}, message = "操作类型参数有误！")
    private EnumBackpackGoodsSource goodsSource;

    @ApiModelProperty(value = "接收用户id(如果是赠送的话必填)", required = true, example = "1")
    private Long targetId;

    public void setOperType(Integer operType) {
        this.operType = operType;
        this.goodsSource = operType == null ? null : EnumUtil.valueOf(EnumBackpackGoodsSource.class, operType);
    }
}

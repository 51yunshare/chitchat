package com.chitchat.portal.vo.base;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 礼物详情
 *
 * Created by Js on 2022/8/17.
 **/
@Data
@ApiModel("礼物详情")
public class GiftInfoVO implements Serializable {

    @ApiModelProperty(value = "礼物id")
    private long id;
    /**
     * 礼物类型
     */
    @ApiModelProperty(value = "礼物类型id")
    private Long giftType;
    @ApiModelProperty(value = "礼物类型名称")
    private String giftTypeName;
    /**
     * 礼物名称
     */
    @ApiModelProperty(value = "礼物名称")
    private String giftName;
    /**
     * 礼物图标
     */
    @ApiModelProperty(value = "礼物图标")
    private String giftIcon;
    /**
     * 礼物价格
     *
     */
    @ApiModelProperty(value = "礼物价格")
    private BigDecimal giftPrice;
    /**
     * 礼物效果
     */
    @ApiModelProperty(value = "礼物效果")
    private String giftEffectUrl;
}

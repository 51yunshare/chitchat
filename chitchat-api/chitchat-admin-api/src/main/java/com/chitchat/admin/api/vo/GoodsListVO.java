package com.chitchat.admin.api.vo;

import com.alibaba.fastjson.JSONArray;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by Js on 2022/9/21 .
 **/
@Data
@ApiModel("商品信息")
public class GoodsListVO implements Serializable {
    /**
     * 商品id
     */
    @ApiModelProperty("商品id")
    private Long id;
    /**
     * 商品分类
     */
    @ApiModelProperty("商品分类id")
    private Long goodsCategoryId;
    @ApiModelProperty("商品分类名称")
    private String goodsCategoryName;
    /**
     * 商品名称
     */
    @ApiModelProperty("商品名称")
    private String goodsName;
    /**
     * 商品组图
     */
    @ApiModelProperty("商品组图")
    private JSONArray goodsImg;
    /**
     * 商品效果
     */
    @ApiModelProperty("商品效果图")
    private String goodsEffectImg;
    /**
     * 商品封面
     */
    @ApiModelProperty("商品封面")
    private String goodsCover;
    /**
     * 商品排序
     */
    @ApiModelProperty("商品排序")
    private Integer sort;
    /**
     * 商品价格
     */
    @ApiModelProperty("商品价格")
    private BigDecimal goodsPrice;
    /**
     * 商品促销价
     */
    @ApiModelProperty("商品促销价")
    private BigDecimal promotionPrice;
    /**
     * 商品时长
     */
    @ApiModelProperty("商品时长")
    private Integer duration;

    @ApiModelProperty("商品备注")
    private String memo;
}

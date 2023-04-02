package com.chitchat.admin.bean;

import com.alibaba.fastjson.JSONArray;
import com.chitchat.common.base.BaseBean;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

/**
 * 商品信息
 *
 * Created by Js on 2022/9/20.
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GoodsInfo extends BaseBean {

    private static final long serialVersionUID = 1L;
    /**
     * 商品id
     */
    private Long id;
    /**
     * 商品分类
     */
    private Long goodsCategoryId;
    private String goodsCategoryName;
    /**
     * 商品名称
     */
    private String goodsName;
    /**
     * 商品组图
     */
    private JSONArray goodsImg;
    /**
     * 商品效果
     */
    private String goodsEffectImg;
    /**
     * 商品封面
     */
    private String goodsCover;
    /**
     * 商品排序
     */
    private Integer sort;
    /**
     * 商品价格
     */
    private BigDecimal goodsPrice;
    /**
     * 商品促销价
     */
    private BigDecimal promotionPrice;
    /**
     * 商品时长
     */
    private Integer duration;
    /**
     * 商品销量
     */
    private Integer sales;
    /**
     * 商品发布状态
     */
    private Integer publishStatus;
    /**
     * 新品状态
     */
    private Integer newStatus;
    /**
     * 商品推荐状态
     */
    private Integer recommendStatus;
    /**
     * 成长值
     */
    private Integer giftGrowth;
}

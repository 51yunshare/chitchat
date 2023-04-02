package com.chitchat.admin.api.vo;

import com.alibaba.fastjson.JSONArray;
import com.chitchat.common.enumerate.EnumYesOrNo;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 商品信息
 *
 * Created by Js on 2022/9/20.
 **/
@Data
@Accessors(chain = true)
public class GoodsDetailVO implements Serializable {

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

    /**
     * 备注
     */
    private String memo;

    /**
     * 删除状态
     */
    private Integer deleted;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createdTime;
}

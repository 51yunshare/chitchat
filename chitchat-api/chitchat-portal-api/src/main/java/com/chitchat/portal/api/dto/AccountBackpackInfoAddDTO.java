package com.chitchat.portal.api.dto;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * 用户背包信息
 *
 * Created by Js on 2022/8/27 .
 */
@Data
@Builder
public class AccountBackpackInfoAddDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 订单id
     */
    private Long orderId;
    /**
     * 订单编号
     */
    private String orderSn;
    /**
     * 账号id
     */
    private Long accountId;
    /**
     * 商品id
     */
    private Long goodsId;
    /**
     * 商品名称
     */
    private String goodsName;
    /**
     * 分类id
     */
    private Long goodsCategoryId;
    /**
     * 分类名称
     */
    private String goodsCategoryName;
    /**
     * 商品封面
     */
    private String goodsCover;
    /**
     * 商品效果图
     */
    private String goodsEffectImg;
    /**
     * 使用时长
     */
    private Integer duration;
    /**
     * 是否激活
     */
    private Integer activateStatus;
    /**
     * 是否过期
     */
    private Integer overdueStatus;
    /**
     * 是否使用
     */
    private Integer usedStatus;
    /**
     * 背包商品来源(1-购买2-赠送)
     */
    private Integer goodsSource;
    /**
     * 赠送人id
     */
    private Long giveAccountId;
}

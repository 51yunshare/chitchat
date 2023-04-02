package com.chitchat.admin.vo.base;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 礼物详情
 *
 * Created by Js on 2022/8/17.
 **/
@Data
public class GiftInfoVo implements Serializable {

    private static final long serialVersionUID = 6186349557745918628L;
    private long id;
    /**
     * 礼物类型
     */
    private Long giftType;
    private String giftTypeName;
    /**
     * 礼物名称
     */
    private String giftName;
    /**
     * 礼物图标
     */
    private String giftIcon;
    /**
     * 礼物价格
     *
     */
    private BigDecimal giftPrice;
    /**
     * 礼物效果
     */
    private String giftEffectUrl;
}

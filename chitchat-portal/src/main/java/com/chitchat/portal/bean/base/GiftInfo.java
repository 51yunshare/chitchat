package com.chitchat.portal.bean.base;

import com.chitchat.common.base.BaseBean;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 礼物信息
 *
 * Created by Js on 2022/8/27 .
 */
@Data
public class GiftInfo extends BaseBean {

    private static final long serialVersionUID = 1L;
    /**
     *
     */
    private Long id;
    /**
     * 礼物类型-字典
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
     */
    private BigDecimal giftPrice;
    /**
     * 礼物效果
     */
    private String giftEffectUrl;
}

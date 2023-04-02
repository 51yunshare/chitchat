package com.chitchat.portal.bean.account;

import com.chitchat.common.base.BaseBean;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * 用户礼物关联信息
 *
 * Created by Js on 2022/8/27 .
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccountGiftLink extends BaseBean {

    private static final long serialVersionUID = 1L;
    /**
     *
     */
    private Long id;
    /**
     * 礼物Id
     */
    private Long giftId;
    /**
     * 礼物价格
     */
    private BigDecimal giftPrice;
    /**
     * 总价值
     */
    private BigDecimal giftAmount;
    /**
     * 礼物数量
     */
    private Integer giftNum;

    /**
     * 接受礼物用户
     */
    private Long targetId;
    /**
     * 房间id
     */
    private Long roomId;
    /**
     * 发送者账号id
     */
    private Long accountId;
}

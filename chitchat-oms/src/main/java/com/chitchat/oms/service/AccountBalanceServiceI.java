package com.chitchat.oms.service;

import com.chitchat.common.base.UserDto;
import com.chitchat.oms.bean.account.AccountBackpack;
import com.chitchat.portal.api.enumerate.EnumBalanceFlowType;

import java.math.BigDecimal;

/**
 * Created by Js on 2022/9/29 .
 **/
public interface AccountBalanceServiceI {

    /**
     * 账号流水扣减
     *
     * @param userDto
     * @param orderId
     * @param orderSn
     * @param goodsPrice
     * @param balanceFlowType
     * @return
     */
    boolean accountBalanceSub(UserDto userDto, Long orderId, String orderSn, BigDecimal goodsPrice, EnumBalanceFlowType balanceFlowType);

    /**
     * 背包道具新增
     *
     * @param backpack
     */
    void doAddAccountBackpack(AccountBackpack backpack);

    /**
     * 处理背包失效道具
     *
     * @param backpackId
     * @return
     */
    boolean updateBackpackInvalidGoods(Long backpackId);
}

package com.chitchat.portal.service.account;

import com.chitchat.common.base.BaseServicesI;
import com.chitchat.common.base.ResultTemplate;
import com.chitchat.portal.bean.account.BalanceInfo;
import com.chitchat.portal.dto.account.BalancePageListRequestDTO;

import java.math.BigDecimal;

/**
 * 余额
 *
 * Created by Js on 2022/9/16 .
 **/
public interface BalanceInfoServiceI extends BaseServicesI<BalanceInfo> {

    /**
     * 余额查询
     *
     * @param dto
     * @return
     */
    ResultTemplate getList(BalancePageListRequestDTO dto);

    /**
     * 查询用户余额
     *
     * @param accountId
     * @return
     */
    BalanceInfo getByAccountId(Long accountId);

    /**
     * 用户余额判断和扣减
     *
     * @param accountId
     * @param giftAmount
     */
    boolean accountBalanceAndSub(Long accountId, BigDecimal giftAmount);

    /**
     * 用户余额判断
     *
     * @param accountId
     * @param goodsPrice
     * @return
     */
    boolean checkBalance(Long accountId, BigDecimal goodsPrice);
}

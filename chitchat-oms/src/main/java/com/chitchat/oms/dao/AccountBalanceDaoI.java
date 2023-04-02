package com.chitchat.oms.dao;

import com.chitchat.common.base.BaseDaoI;
import com.chitchat.oms.bean.account.AccountBackpack;
import com.chitchat.oms.bean.account.AccountBalanceFlowInfo;
import com.chitchat.oms.bean.account.AccountBalanceInfo;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;

/**
 * Created by Js on 2022/9/29 .
 **/
public interface AccountBalanceDaoI extends BaseDaoI<AccountBalanceInfo> {

    /**
     * 获取账号余额
     *
     * @param accountId
     * @return
     */
    AccountBalanceInfo getByAccountId(Long accountId);

    /**
     * 扣减账户余额
     *
     * @param accountId
     * @param amount
     * @return
     */
    int accountBalanceSubCoins(@Param("accountId") Long accountId, @Param("amount") BigDecimal amount);

    /**
     * 生成余额流水
     *
     * @param balanceFlowInfo
     */
    int insertBalanceFlowInfo(AccountBalanceFlowInfo balanceFlowInfo);

    /**
     * 背包道具新增
     *
     * @param backpack
     */
    int addAccountBackpack(AccountBackpack backpack);

    /**
     * 修改背包失效道具
     *
     * @param backpackId
     * @return
     */
    int updateBackpackInvalidGoods(@Param("backpackId") Long backpackId);
}

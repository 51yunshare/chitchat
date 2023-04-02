package com.chitchat.portal.dao.account;

import com.chitchat.common.base.BaseDaoI;
import com.chitchat.portal.bean.account.BalanceInfo;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;

public interface BalanceInfoDaoI extends BaseDaoI<BalanceInfo> {

    /**
     * 查询用户余额
     *
     * @param accountId
     * @return
     */
    BalanceInfo getByAccountId(@Param("accountId") Long accountId);

    /**
     * 用户余额扣减
     *
     * @param accountId
     * @param giftAmount
     * @return
     */
    int accountBalanceSubCoins(@Param("accountId") Long accountId, @Param("giftAmount") BigDecimal giftAmount);
}

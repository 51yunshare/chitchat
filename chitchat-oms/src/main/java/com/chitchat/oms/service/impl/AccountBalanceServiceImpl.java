package com.chitchat.oms.service.impl;

import com.chitchat.common.base.BaseServicesImpl;
import com.chitchat.common.base.CodeMsg;
import com.chitchat.common.base.UserDto;
import com.chitchat.common.exception.ChitchatException;
import com.chitchat.common.util.BigDecimalUtil;
import com.chitchat.oms.bean.account.AccountBackpack;
import com.chitchat.oms.bean.account.AccountBalanceFlowInfo;
import com.chitchat.oms.bean.account.AccountBalanceInfo;
import com.chitchat.oms.dao.AccountBalanceDaoI;
import com.chitchat.oms.service.AccountBalanceServiceI;
import com.chitchat.portal.api.enumerate.EnumBalanceFlowType;
import com.chitchat.portal.api.enumerate.EnumBalanceType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

/**
 * Created by Js on 2022/9/29 .
 **/
@Service
public class AccountBalanceServiceImpl extends BaseServicesImpl<AccountBalanceInfo, AccountBalanceDaoI> implements AccountBalanceServiceI {

    /**
     * 账号流水扣减
     *
     * @param userDto
     * @param orderId
     * @param orderSn
     * @param amount
     * @param balanceFlowType
     * @return
     */
    @Override
    public boolean accountBalanceSub(UserDto userDto, Long orderId, String orderSn, BigDecimal amount, EnumBalanceFlowType balanceFlowType) {
        final AccountBalanceInfo prdBalanceInfo = baseDaoT.getByAccountId(userDto.getId());
        if (prdBalanceInfo == null || prdBalanceInfo.getCoinsBalance().compareTo(amount) < 0) {
            throw new ChitchatException(CodeMsg.SERVER_ERROR, "当前金币余额不足，请进行充值！");
        }
        int num = baseDaoT.accountBalanceSubCoins(userDto.getId(), amount);
        if (num > 0){
            //todo 流水记录
            final AccountBalanceInfo postBalanceInfo = baseDaoT.getByAccountId(userDto.getId());
            AccountBalanceFlowInfo balanceFlowInfo = AccountBalanceFlowInfo.builder()
                    .type(EnumBalanceType.金币.getIndex())
                    .accountId(userDto.getId())
                    .balanceId(prdBalanceInfo.getId())
                    .flowType(balanceFlowType.getIndex())
                    .orderId(orderId)
                    .orderSn(orderSn)
                    .preBalanceNum(prdBalanceInfo.getCoinsBalance() == null ? BigDecimal.ZERO : prdBalanceInfo.getCoinsBalance())
                    .curOrderNum(BigDecimalUtil.getMinus(amount))
                    .postBalanceNum(postBalanceInfo.getCoinsBalance() == null ? BigDecimal.ZERO : postBalanceInfo.getCoinsBalance())
                    .build();
            balanceFlowInfo.setCreatedUserId(userDto.getId());
            balanceFlowInfo.setCreatedUserName(userDto.getNickName());
            baseDaoT.insertBalanceFlowInfo(balanceFlowInfo);
            return true;
        }
        return false;
    }

    /**
     * 背包道具新增
     *
     * @param backpack
     */
    @Override
    public void doAddAccountBackpack(AccountBackpack backpack) {
        baseDaoT.addAccountBackpack(backpack);
    }

    /**
     * 处理背包失效道具
     *
     * @param backpackId
     * @return
     */
    @Override
    @Transactional
    public boolean updateBackpackInvalidGoods(Long backpackId) {
        int num = baseDaoT.updateBackpackInvalidGoods(backpackId);
        if (num <= 0){
            return false;
        }
        return true;
    }
}

package com.chitchat.portal.service.account;

import com.chitchat.common.base.BaseServicesI;
import com.chitchat.portal.bean.account.AccountExpFlowLog;
import com.chitchat.portal.enumerate.EnumExpSourceType;
import com.chitchat.portal.enumerate.EnumLevelType;

/**
 * Created by Js on 2022/12/16 .
 **/
public interface AccountExpFlowLogServiceI extends BaseServicesI<AccountExpFlowLog> {

    /**
     * 获取当天总的金币数/分钟数
     *
     *
     * @param accountId
     * @param member
     * @param giveGift
     * @param today
     * @return
     */
    int getCoinsSumTheDay(Long accountId, EnumLevelType member, EnumExpSourceType giveGift, String today);

    /**
     * 用户等级经验处理
     *
     * @param accountId
     * @param member
     * @param giveGift
     * @param intValue
     * @param today
     */
    void userMemberLevelExpHandler(Long accountId, EnumLevelType member, EnumExpSourceType giveGift, int intValue, String today);
}

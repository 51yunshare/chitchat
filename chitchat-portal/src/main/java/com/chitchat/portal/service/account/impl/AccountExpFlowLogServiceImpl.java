package com.chitchat.portal.service.account.impl;

import cn.hutool.core.date.DateUtil;
import com.chitchat.common.base.BaseServicesImpl;
import com.chitchat.portal.bean.account.AccountExpFlowLog;
import com.chitchat.portal.bean.account.AccountInfo;
import com.chitchat.portal.bean.account.AccountLevel;
import com.chitchat.portal.config.LevelExpConfigProperties;
import com.chitchat.portal.dao.account.AccountExpFlowLogDaoI;
import com.chitchat.portal.enumerate.EnumChangeType;
import com.chitchat.portal.enumerate.EnumExpSourceType;
import com.chitchat.portal.enumerate.EnumLevelType;
import com.chitchat.portal.service.account.AccountExpFlowLogServiceI;
import com.chitchat.portal.service.account.AccountInfoServiceI;
import com.chitchat.portal.service.account.AccountLevelServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 经验值记录
 *
 * Created by Js on 2022/12/16 .
 **/
@Service
public class AccountExpFlowLogServiceImpl extends BaseServicesImpl<AccountExpFlowLog, AccountExpFlowLogDaoI> implements AccountExpFlowLogServiceI {

    @Autowired
    private LevelExpConfigProperties levelExpConfigProperties;
    @Autowired
    private AccountInfoServiceI accountInfoServiceI;
    @Autowired
    private AccountLevelServiceI accountLevelServiceI;

    /**
     * 获取当天总的金币数/分钟数
     *
     *
     * @param accountId
     * @param levelType
     * @param sourceType
     * @param today
     * @return
     */
    @Override
    public int getCoinsSumTheDay(Long accountId, EnumLevelType levelType, EnumExpSourceType sourceType, String today) {
        return baseDaoT.getCoinsSumTheDay(accountId, levelType.getIndex(), sourceType.getIndex(), today);
    }

    /**
     * 用户等级经验处理
     *
     * @param accountId
     * @param levelType
     * @param sourceType
     * @param consumptionNum
     * @param today
     */
    @Override
    public void userMemberLevelExpHandler(Long accountId, EnumLevelType levelType, EnumExpSourceType sourceType, int consumptionNum, String today) {
        //当天已经累计的金币数/分钟数
        int totalCoinsOrMins = baseDaoT.getCoinsSumTheDay(accountId, levelType.getIndex(), sourceType.getIndex(), today);
        //不同类型经验值的每天上限不一样，经验值比例
        if(sourceType.getIndex() == EnumExpSourceType.GIVE_GIFT.getIndex()){
            checkCurrentLimit(accountId, levelType, sourceType, consumptionNum, totalCoinsOrMins,
                    levelExpConfigProperties.getGiveGiftLimit(), levelExpConfigProperties.getGiveGift(), today);
        }else if (sourceType.getIndex() == EnumExpSourceType.ROOM_DURATION.getIndex()){
            checkCurrentLimit(accountId, levelType, sourceType, consumptionNum, totalCoinsOrMins,
                    levelExpConfigProperties.getRoomDurationLimit(), levelExpConfigProperties.getRoomDuration(), today);
        }
    }

    /**
     * 判断是否达到当天上限
     * @param accountId
     * @param levelType
     * @param sourceType
     * @param consumptionNum
     * @param totalCoinsOrMins
     * @param limitNum
     * @param ratioParam
     * @param today
     */
    private void checkCurrentLimit(Long accountId, EnumLevelType levelType, EnumExpSourceType sourceType,
                                   int consumptionNum, int totalCoinsOrMins, Integer limitNum, Integer ratioParam, String today) {
        //判断送礼金币是否超过当天的总值
        if (totalCoinsOrMins < limitNum.intValue()){
            //距离当天限额差多少
            int diff = limitNum.intValue() - totalCoinsOrMins;
            Integer expMultiplier = diff;
            if (diff > consumptionNum){
                expMultiplier = consumptionNum;
            }
            baseDaoT.insert(AccountExpFlowLog.builder()
                    .accountId(accountId)
                    .expType(levelType.getIndex())
                    .changeType(EnumChangeType.ADD.getIndex())
                    .expMultiplier(expMultiplier)
                    .changeCount(expMultiplier * ratioParam)
                    .sourceType(sourceType.getIndex())
                    .expDate(DateUtil.parseDate(today))
                    .createdUser(accountId.toString())
                    .build());
            //回写用户经验值
            AccountInfo accountInfo = accountInfoServiceI.getById(accountId);
            Integer lastExp = accountInfo.getAccountLevelExp().intValue() + (expMultiplier * ratioParam);
            //获取经验值对应的等级【满级100】
            AccountLevel accountLevel = accountLevelServiceI.getLevelByExp(lastExp);
            long level = Long.parseLong(accountLevel.getLevelName());
            accountInfoServiceI.updateByPrimaryKeySelective(AccountInfo.builder()
                    .id(accountId)
                    .accountLevelExp(lastExp)
                    .accountLevelId(accountLevel == null ? 100 : level)
                    .build());
        }
    }
}

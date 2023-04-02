package com.chitchat.portal.service.account.impl;

import cn.hutool.core.util.ObjectUtil;
import com.chitchat.common.constant.RedisConstants;
import com.chitchat.common.redis.RedisTemplateUtil;
import com.chitchat.portal.bean.account.AccountLevel;
import com.chitchat.portal.bean.account.AccountLevelGame;
import com.chitchat.portal.dao.account.AccountLevelDaoI;
import com.chitchat.portal.dao.account.AccountLevelGameDaoI;
import com.chitchat.portal.service.account.AccountLevelServiceI;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户等级业务相关
 *
 * Created by Js on 2022/11/21 .
 **/
@Service
@RequiredArgsConstructor
public class AccountLevelServiceImpl implements AccountLevelServiceI {

    private final RedisTemplateUtil redisTemplateUtil;
    private final AccountLevelDaoI accountLevelDaoI;
    private final AccountLevelGameDaoI accountLevelGameDaoI;

    /**
     * 刷新用户等级信息
     *
     */
    @Override
    public void refreshAccountLevel() {
        redisTemplateUtil.del(RedisConstants.ACCOUNT_LEVEL);
        //获取所有的会员等级信息
        List<AccountLevel> accountLevelList = accountLevelDaoI.list(null);
        if (ObjectUtil.length(accountLevelList) > 0){
            Map<String, List<Integer>> accountLevel = new HashMap<>();
            accountLevelList.forEach(aLevel ->{
                accountLevel.put(aLevel.getLevelName(), Arrays.asList(aLevel.getStartGrowthPoint(), aLevel.getGrowthPoint()));
            });
            redisTemplateUtil.hSetAll(RedisConstants.ACCOUNT_LEVEL + "MEMBER", accountLevel);
        }
        //游戏等级信息
        List<AccountLevelGame> accountLevelGameList = accountLevelGameDaoI.list(null);
        if (ObjectUtil.length(accountLevelGameList) > 0){
            Map<String, List<Integer>> accountLevel = new HashMap<>();
            accountLevelGameList.forEach(aLevel ->{
                accountLevel.put(aLevel.getLevelName(), Arrays.asList(aLevel.getStartGrowthPoint(), aLevel.getGrowthPoint()));
            });
            redisTemplateUtil.hSetAll(RedisConstants.ACCOUNT_LEVEL + "GAME", accountLevel);
        }
    }

    /**
     * 根据经验值获取对应的等级
     *
     * @param lastExp
     * @return
     */
    @Override
    public AccountLevel getLevelByExp(Integer lastExp) {
        return accountLevelDaoI.getLevelByExp(lastExp);
    }
}

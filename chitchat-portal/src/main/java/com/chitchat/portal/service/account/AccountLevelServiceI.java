package com.chitchat.portal.service.account;

import com.chitchat.portal.bean.account.AccountLevel;

/**
 * 用户等级业务
 *
 * Created by Js on 2022/11/21 .
 **/
public interface AccountLevelServiceI {

    /**
     * 刷新用户等级信息
     *
     */
    void refreshAccountLevel();

    /**
     * 根据经验值获取对应的等级
     *
     * @param lastExp
     * @return
     */
    AccountLevel getLevelByExp(Integer lastExp);
}

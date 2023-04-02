package com.chitchat.portal.dao.account;

import com.chitchat.common.base.BaseDaoI;
import com.chitchat.portal.bean.account.AccountExpFlowLog;
import org.apache.ibatis.annotations.Param;

public interface AccountExpFlowLogDaoI extends BaseDaoI<AccountExpFlowLog> {

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
    int getCoinsSumTheDay(@Param("accountId") Long accountId, @Param("levelType") Integer levelType,
                          @Param("sourceType") Integer sourceType, @Param("today") String today);
}

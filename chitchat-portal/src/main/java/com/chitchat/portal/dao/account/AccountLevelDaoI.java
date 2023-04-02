package com.chitchat.portal.dao.account;

import com.chitchat.common.base.BaseDaoI;
import com.chitchat.portal.bean.account.AccountLevel;
import org.apache.ibatis.annotations.Param;

public interface AccountLevelDaoI extends BaseDaoI<AccountLevel> {

    /**
     * 根据经验值获取对应的等级
     *
     * @param lastExp
     * @return
     */
    AccountLevel getLevelByExp(@Param("lastExp") Integer lastExp);
}

package com.chitchat.portal.dao.account;

import com.chitchat.common.base.BaseDaoI;
import com.chitchat.portal.bean.account.AccountVisitorsInfo;
import org.apache.ibatis.annotations.Param;

public interface AccountVisitorsInfoDaoI extends BaseDaoI<AccountVisitorsInfo> {

    /**
     * 判断访客是否访问过
     *
     * @param accountId
     * @param visitorId
     * @return
     */
    AccountVisitorsInfo getByAccountIdAndVisitorId(@Param("accountId") Long accountId, @Param("visitorId") Long visitorId);
}

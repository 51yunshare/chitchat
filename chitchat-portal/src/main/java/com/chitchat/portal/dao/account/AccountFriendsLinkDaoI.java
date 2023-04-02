package com.chitchat.portal.dao.account;

import com.chitchat.common.base.BaseDaoI;
import com.chitchat.portal.bean.account.AccountFriendsLink;
import org.apache.ibatis.annotations.Param;

public interface AccountFriendsLinkDaoI extends BaseDaoI<AccountFriendsLink> {

    /**
     * 查询好友关联关系
     *
     * @param accountId
     * @param friendAccountId
     * @return
     */
    AccountFriendsLink getByAccountIdAndFriendId(@Param("accountId") Long accountId, @Param("friendAccountId") Long friendAccountId);
}

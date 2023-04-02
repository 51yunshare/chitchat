package com.chitchat.portal.service.account;

import com.chitchat.common.base.BaseServicesI;
import com.chitchat.portal.bean.account.AccountFriendsLink;

/**
 * 用户朋友service接口
 */
public interface AccountFriendsLinkServiceI extends BaseServicesI<AccountFriendsLink> {

    /**
     * 查询好友关联关系
     *
     * @param accountId
     * @param friendAccountId
     * @return
     */
    AccountFriendsLink getByAccountIdAndFriendId(Long accountId, Long friendAccountId);
}

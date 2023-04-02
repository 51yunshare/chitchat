package com.chitchat.portal.service.account.impl;

import com.chitchat.common.base.BaseServicesImpl;
import com.chitchat.portal.bean.account.AccountFriendsLink;
import com.chitchat.portal.dao.account.AccountFriendsLinkDaoI;
import com.chitchat.portal.service.account.AccountFriendsLinkServiceI;
import org.springframework.stereotype.Service;

/**
 * 用户朋友service实现类
 */
@Service
public class AccountFriendsLinkServiceImpl extends BaseServicesImpl<AccountFriendsLink, AccountFriendsLinkDaoI> implements AccountFriendsLinkServiceI {

    /**
     * 查询好友关联关系
     *
     * @param accountId
     * @param friendAccountId
     * @return
     */
    @Override
    public AccountFriendsLink getByAccountIdAndFriendId(Long accountId, Long friendAccountId) {
        return baseDaoT.getByAccountIdAndFriendId(accountId, friendAccountId);
    }
}

package com.chitchat.portal.service.account.impl;

import com.chitchat.common.base.BaseServicesImpl;
import com.chitchat.portal.bean.account.AccountFollowLink;
import com.chitchat.portal.dao.account.AccountFollowLinkDaoI;
import com.chitchat.portal.dto.account.AccountCenterPageListRequestDTO;
import com.chitchat.portal.service.account.AccountFollowLinkServiceI;
import com.chitchat.portal.vo.account.AccountFollowLinkVO;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 用户关注service实现类
 */
@Service
public class AccountFollowLinkServiceImpl extends BaseServicesImpl<AccountFollowLink, AccountFollowLinkDaoI> implements AccountFollowLinkServiceI {

    /**
     * 我关注列表
     *
     * @param dto
     * @return
     */
    @Override
    public List<AccountFollowLinkVO> followingList(AccountCenterPageListRequestDTO dto) {
        return baseDaoT.getFollowingList(dto);
    }

    /**
     * 我的粉丝
     *
     * @param dto
     * @return
     */
    @Override
    public List<AccountFollowLinkVO> getFollowerList(AccountCenterPageListRequestDTO dto) {
        return baseDaoT.getFollowerList(dto);
    }

    /**
     * 判断用户关注信息
     *
     * @param followType
     * @param targetId
     * @param accountId
     * @return
     */
    @Override
    public AccountFollowLink getFollowByTargetIdAndAccountId(Integer followType, Long targetId, Long accountId) {
        return baseDaoT.getFollowByTargetIdAndAccountId(followType, targetId, accountId);
    }
}

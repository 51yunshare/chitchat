package com.chitchat.portal.service.account;

import com.chitchat.common.base.BaseServicesI;
import com.chitchat.portal.bean.account.AccountFollowLink;
import com.chitchat.portal.dto.account.AccountCenterPageListRequestDTO;
import com.chitchat.portal.vo.account.AccountFollowLinkVO;

import java.util.List;

/**
 * 用户关注service接口
 */
public interface AccountFollowLinkServiceI extends BaseServicesI<AccountFollowLink> {

    /**
     * 我关注列表
     *
     * @param dto
     * @return
     */
    List<AccountFollowLinkVO> followingList(AccountCenterPageListRequestDTO dto);

    /**
     * 我的粉丝
     *
     * @param dto
     * @return
     */
    List<AccountFollowLinkVO> getFollowerList(AccountCenterPageListRequestDTO dto);

    /**
     * 判断用户关注信息
     *
     * @param followType
     * @param targetId
     * @param accountId
     * @return
     */
    AccountFollowLink getFollowByTargetIdAndAccountId(Integer followType, Long targetId, Long accountId);
}

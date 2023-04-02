package com.chitchat.portal.dao.account;

import com.chitchat.common.base.BaseDaoI;
import com.chitchat.portal.bean.account.AccountFollowLink;
import com.chitchat.portal.dto.account.AccountCenterPageListRequestDTO;
import com.chitchat.portal.vo.account.AccountFollowLinkVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 用户关注
 *
 * Created by Js on 2022/8/27 .
 */
public interface AccountFollowLinkDaoI extends BaseDaoI<AccountFollowLink> {

    /**
     * 我关注列表
     *
     * @param dto
     * @return
     */
    List<AccountFollowLinkVO> getFollowingList(AccountCenterPageListRequestDTO dto);

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
    AccountFollowLink getFollowByTargetIdAndAccountId(@Param("followType") Integer followType, @Param("targetId") Long targetId,
                                                      @Param("accountId") Long accountId);
}

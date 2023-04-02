package com.chitchat.portal.service.account.impl;

import com.chitchat.common.base.BaseServicesImpl;
import com.chitchat.portal.bean.account.AccountGiftLink;
import com.chitchat.portal.dao.account.AccountGiftLinkDaoI;
import com.chitchat.portal.service.account.AccountGiftLinkServiceI;
import com.chitchat.portal.vo.account.AccountDetailVO;
import com.chitchat.portal.vo.room.RoomContributionRankVO;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 用户礼物关联service实现类
 */
@Service
public class AccountGiftLinkServiceImpl extends BaseServicesImpl<AccountGiftLink, AccountGiftLinkDaoI> implements AccountGiftLinkServiceI {

    /**
     * 房间贡献榜单
     *
     * @param roomId
     * @param type
     * @return
     */
    @Override
    public List<RoomContributionRankVO> getContributionRank(Long roomId, Integer type) {
        return baseDaoT.getContributionRank(roomId, type);
    }

    /**
     * 用户收到所有礼物
     *
     * @param accountId
     * @return
     */
    @Override
    public List<AccountDetailVO.ReceivedGiftInfo> getReceivedGiftByAccountId(Long accountId) {
        return baseDaoT.getReceivedGiftByAccountId(accountId);
    }
}

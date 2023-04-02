package com.chitchat.portal.service.account;

import com.chitchat.common.base.BaseServicesI;
import com.chitchat.portal.bean.account.AccountGiftLink;
import com.chitchat.portal.vo.account.AccountDetailVO;
import com.chitchat.portal.vo.room.RoomContributionRankVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 用户礼物关联service接口
 */
public interface AccountGiftLinkServiceI extends BaseServicesI<AccountGiftLink> {

    /**
     * 房间贡献榜单
     *
     * @param roomId
     * @param type
     * @return
     */
    List<RoomContributionRankVO> getContributionRank(@Param("roomId") Long roomId, Integer type);

    /**
     * 用户收到所有礼物
     *
     * @param accountId
     * @return
     */
    List<AccountDetailVO.ReceivedGiftInfo> getReceivedGiftByAccountId(Long accountId);
}

package com.chitchat.portal.dao.account;

import com.chitchat.common.base.BaseDaoI;
import com.chitchat.portal.bean.account.AccountGiftLink;
import com.chitchat.portal.vo.account.AccountDetailVO;
import com.chitchat.portal.vo.room.RoomContributionRankVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 用户礼物关联
 *
 * Created by Js on 2022/8/27 .
 */
public interface AccountGiftLinkDaoI extends BaseDaoI<AccountGiftLink> {

    /**
     * 房间贡献榜单
     *
     * @param roomId
     * @param type
     * @return
     */
    List<RoomContributionRankVO> getContributionRank(@Param("roomId") Long roomId, @Param("type") Integer type);

    /**
     * 用户收到所有礼物
     *
     * @param accountId
     * @return
     */
    List<AccountDetailVO.ReceivedGiftInfo> getReceivedGiftByAccountId(@Param("accountId") Long accountId);
}

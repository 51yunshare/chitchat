package com.chitchat.portal.dao.room;

import com.chitchat.common.base.BaseDaoI;
import com.chitchat.portal.bean.room.RoomGameAccountLink;
import com.chitchat.portal.vo.account.AccountBaseInfoVO;
import com.chitchat.portal.vo.account.AccountDetailVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RoomGameAccountLinkDaoI extends BaseDaoI<RoomGameAccountLink> {

    /**
     * 游戏房间id和账号id获取关联信息
     *
     * @param roomGameId
     * @param accountId
     * @return
     */
    RoomGameAccountLink getByRoomIdAndAccountId(@Param("roomGameId") Long roomGameId, @Param("accountId") Long accountId);

    /**
     * 游戏房间用户信息
     *
     * @param roomId
     * @param accountId
     * @return
     */
    List<AccountBaseInfoVO> getGameAccountListByRoomId(@Param("roomId") Long roomId, @Param("accountId") Long accountId);

    /**
     * 获取用户最近玩的游戏
     *
     * @param accountId
     * @return
     */
    List<AccountDetailVO.AccountRecentGameVO> getRecentGameByAccountId(@Param("accountId") Long accountId);
}

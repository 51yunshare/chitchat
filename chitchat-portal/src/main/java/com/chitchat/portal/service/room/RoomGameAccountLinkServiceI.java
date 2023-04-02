package com.chitchat.portal.service.room;

import com.chitchat.common.base.BaseServicesI;
import com.chitchat.portal.bean.room.RoomGameAccountLink;
import com.chitchat.portal.vo.account.AccountBaseInfoVO;
import com.chitchat.portal.vo.account.AccountDetailVO;

import java.util.List;

/**
 * Created by Js on 2022/10/29 .
 **/
public interface RoomGameAccountLinkServiceI extends BaseServicesI<RoomGameAccountLink> {

    /**
     * 游戏房间id和账号id获取关联信息
     *
     * @param roomGameId
     * @param accountId
     * @return
     */
    RoomGameAccountLink getByRoomIdAndAccountId(Long roomGameId, Long accountId);

    /**
     * 游戏房间用户信息
     *
     * @param roomId
     * @param accountId
     * @return
     */
    List<AccountBaseInfoVO> getGameAccountListByRoomId(Long roomId, Long accountId);

    /**
     * 获取用户最近玩的游戏
     *
     * @param accountId
     * @return
     */
    List<AccountDetailVO.AccountRecentGameVO> getRecentGameByAccountId(Long accountId);
}

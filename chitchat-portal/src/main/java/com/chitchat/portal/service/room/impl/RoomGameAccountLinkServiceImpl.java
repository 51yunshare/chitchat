package com.chitchat.portal.service.room.impl;

import com.chitchat.common.base.BaseServicesImpl;
import com.chitchat.portal.bean.room.RoomGameAccountLink;
import com.chitchat.portal.dao.room.RoomGameAccountLinkDaoI;
import com.chitchat.portal.service.room.RoomGameAccountLinkServiceI;
import com.chitchat.portal.vo.account.AccountBaseInfoVO;
import com.chitchat.portal.vo.account.AccountDetailVO;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 游戏房间与用户关联关系
 *
 * Created by Js on 2022/10/29 .
 **/
@Service
public class RoomGameAccountLinkServiceImpl extends BaseServicesImpl<RoomGameAccountLink, RoomGameAccountLinkDaoI> implements RoomGameAccountLinkServiceI {

    /**
     * 游戏房间id和账号id获取关联信息
     *
     * @param roomGameId
     * @param accountId
     * @return
     */
    @Override
    public RoomGameAccountLink getByRoomIdAndAccountId(Long roomGameId, Long accountId) {
        return baseDaoT.getByRoomIdAndAccountId(roomGameId, accountId);
    }

    /**
     * 游戏房间用户信息
     *
     * @param roomId
     * @param accountId
     * @return
     */
    @Override
    public List<AccountBaseInfoVO> getGameAccountListByRoomId(Long roomId, Long accountId) {
        return baseDaoT.getGameAccountListByRoomId(roomId, accountId);
    }

    /**
     * 获取用户最近玩的游戏
     *
     * @param accountId
     * @return
     */
    @Override
    public List<AccountDetailVO.AccountRecentGameVO> getRecentGameByAccountId(Long accountId) {
        return baseDaoT.getRecentGameByAccountId(accountId);
    }
}

package com.chitchat.portal.service.room.impl;

import com.chitchat.common.base.BaseServicesImpl;
import com.chitchat.portal.bean.room.RoomKickOutAccountLink;
import com.chitchat.portal.dao.room.RoomKickOutAccountLinkDaoI;
import com.chitchat.portal.service.room.RoomKickOutAccountLinkServiceI;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 房间踢出用户表 Service实现
 *
 * Created by Js on 2022/8/26 .
 */
@Service
@Slf4j
public class RoomKickOutAccountLinkServiceImpl extends BaseServicesImpl<RoomKickOutAccountLink, RoomKickOutAccountLinkDaoI> implements RoomKickOutAccountLinkServiceI {

    /**
     * 踢出房间用户查询
     *
     * @param roomId
     * @param accountId
     * @return
     */
    @Override
    public RoomKickOutAccountLink getByRoomIdAndAccountId(Long roomId, Long accountId) {
        return baseDaoT.getByRoomIdAndAccountId(roomId, accountId);
    }
}

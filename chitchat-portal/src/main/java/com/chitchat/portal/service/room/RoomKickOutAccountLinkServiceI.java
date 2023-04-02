package com.chitchat.portal.service.room;

import com.chitchat.common.base.BaseServicesI;
import com.chitchat.portal.bean.room.RoomKickOutAccountLink;

/**
* 房间踢出用户表 Service
 *
 * Created by Js on 2022/8/26 .
*/
public interface RoomKickOutAccountLinkServiceI extends BaseServicesI<RoomKickOutAccountLink> {

    /**
     * 踢出房间用户查询
     *
     * @param roomId
     * @param accountId
     * @return
     */
    RoomKickOutAccountLink getByRoomIdAndAccountId(Long roomId, Long accountId);
}

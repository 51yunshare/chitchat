package com.chitchat.portal.dao.room;

import com.chitchat.common.base.BaseDaoI;
import com.chitchat.portal.bean.room.RoomKickOutAccountLink;
import org.apache.ibatis.annotations.Param;

/**
 * 房间踢出用户表
 *
 * Created by Js on 2022/8/26 .
*/
public interface RoomKickOutAccountLinkDaoI extends BaseDaoI<RoomKickOutAccountLink> {

    /**
     * 踢出房间用户查询
     *
     * @param roomId
     * @param accountId
     * @return
     */
    RoomKickOutAccountLink getByRoomIdAndAccountId(@Param("roomId") Long roomId, @Param("accountId") Long accountId);
}

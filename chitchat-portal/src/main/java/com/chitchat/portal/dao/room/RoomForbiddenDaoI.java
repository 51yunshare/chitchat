package com.chitchat.portal.dao.room;

import com.chitchat.common.base.BaseDaoI;
import com.chitchat.portal.bean.room.RoomForbidden;
import org.apache.ibatis.annotations.Param;

/**
 * 房间禁止用户表
 *
 * Created by Js on 2022/8/26 .
*/
public interface RoomForbiddenDaoI extends BaseDaoI<RoomForbidden> {

    /**
     * 查询禁止房间用户信息通过用户Id和房间id
     *
     * @param roomId
     * @param accountId
     * @return
     */
    RoomForbidden getByRoomIdAndAccountId(@Param("roomId") Long roomId, @Param("accountId") Long accountId);
}

package com.chitchat.portal.dao.room;

import com.chitchat.common.base.BaseDaoI;
import com.chitchat.portal.bean.room.RoomMicStreamLink;
import org.apache.ibatis.annotations.Param;


public interface RoomMicStreamLinkDaoI extends BaseDaoI<RoomMicStreamLink> {

    /**
     * 删除麦位流信息-通过房间id、麦位id和用户id
     *
     * @param roomId
     * @param micId
     * @param accountId
     */
    void deletedByRoomIdAndMicIdAndAccountId(@Param("roomId") Long roomId, @Param("micId") Long micId, @Param("accountId") Long accountId);
}

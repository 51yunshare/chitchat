package com.chitchat.portal.service.room;

import com.chitchat.common.base.BaseServicesI;
import com.chitchat.portal.bean.room.RoomMicStreamLink;

/**
 * 房间麦位流信息
 *
 * Created by Js on 2022/9/16 .
 **/
public interface RoomMicStreamLinkServiceI extends BaseServicesI<RoomMicStreamLink> {

    /**
     * 删除麦位流信息-通过房间id、麦位id和用户id
     *
     * @param roomId
     * @param micId
     * @param accountId
     */
    void deletedByRoomIdAndMicIdAndAccountId(Long roomId, Long micId, Long accountId);
}

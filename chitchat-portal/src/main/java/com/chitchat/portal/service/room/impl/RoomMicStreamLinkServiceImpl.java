package com.chitchat.portal.service.room.impl;

import com.chitchat.common.base.BaseServicesImpl;
import com.chitchat.portal.bean.room.RoomMicStreamLink;
import com.chitchat.portal.dao.room.RoomMicStreamLinkDaoI;
import com.chitchat.portal.service.room.RoomMicStreamLinkServiceI;
import org.springframework.stereotype.Service;

/**
 * 麦位流信
 *
 * Created by Js on 2022/9/16 .
 **/
@Service
public class RoomMicStreamLinkServiceImpl extends BaseServicesImpl<RoomMicStreamLink, RoomMicStreamLinkDaoI> implements RoomMicStreamLinkServiceI {

    /**
     * 删除麦位流信息-通过房间id、麦位id和用户id
     *
     * @param roomId
     * @param micId
     * @param accountId
     */
    @Override
    public void deletedByRoomIdAndMicIdAndAccountId(Long roomId, Long micId, Long accountId) {
        baseDaoT.deletedByRoomIdAndMicIdAndAccountId(roomId, micId, accountId);
    }
}

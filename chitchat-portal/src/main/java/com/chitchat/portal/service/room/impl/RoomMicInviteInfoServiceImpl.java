package com.chitchat.portal.service.room.impl;

import com.chitchat.common.base.BaseServicesImpl;
import com.chitchat.portal.bean.room.RoomMicInviteInfo;
import com.chitchat.portal.dao.room.RoomMicInviteInfoDaoI;
import com.chitchat.portal.service.room.RoomMicInviteInfoServiceI;
import org.springframework.stereotype.Service;

/**
 * 房间用户邀请上麦信息
 *
 * Created by Js on 2022/10/21 .
 **/
@Service
public class RoomMicInviteInfoServiceImpl extends BaseServicesImpl<RoomMicInviteInfo, RoomMicInviteInfoDaoI> implements RoomMicInviteInfoServiceI {

    /**
     * 查询用户邀请上麦记录
     *
     * @param roomId
     * @param accountId
     * @return
     */
    @Override
    public RoomMicInviteInfo getByRoomAndAccountId(Long roomId, Long accountId) {
        return baseDaoT.getByRoomAndAccountId(roomId, accountId);
    }

    /**
     * 邀请上麦状态修改
     *  @param roomId
     * @param accountId
     */
    @Override
    public void updateInviteStatusByRoomIdAndAccountId(Long roomId, Long accountId) {
        baseDaoT.updateInviteStatusByRoomIdAndAccountId(roomId, accountId);
    }
}

package com.chitchat.portal.service.room;

import com.chitchat.common.base.BaseServicesI;
import com.chitchat.portal.bean.room.RoomMicInviteInfo;

/**
 * 房间用户邀请上麦信息
 *
 * Created by Js on 2022/9/10 .
 **/
public interface RoomMicInviteInfoServiceI extends BaseServicesI<RoomMicInviteInfo> {

    /**
     * 查询用户邀请上麦记录
     *
     * @param roomId
     * @param accountId
     * @return
     */
    RoomMicInviteInfo getByRoomAndAccountId(Long roomId, Long accountId);

    /**
     * 邀请上麦状态修改
     *  @param roomId
     * @param accountId
     */
    void updateInviteStatusByRoomIdAndAccountId(Long roomId, Long accountId);
}

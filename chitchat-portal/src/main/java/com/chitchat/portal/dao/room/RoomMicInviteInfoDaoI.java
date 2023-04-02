package com.chitchat.portal.dao.room;

import com.chitchat.common.base.BaseDaoI;
import com.chitchat.portal.bean.room.RoomMicInviteInfo;
import org.apache.ibatis.annotations.Param;

/**
 * 房间麦位邀请信息
 *
 * Created by Js on 2022/10/21 .
 **/
public interface RoomMicInviteInfoDaoI extends BaseDaoI<RoomMicInviteInfo> {

    /**
     * 查询用户邀请上麦记录
     *
     * @param roomId
     * @param accountId
     * @return
     */
    RoomMicInviteInfo getByRoomAndAccountId(@Param("roomId") Long roomId, @Param("accountId") Long accountId);

    /**
     * 邀请上麦状态修改
     *
     * @param roomId
     * @param accountId
     * @return
     */
    int updateInviteStatusByRoomIdAndAccountId(@Param("roomId") Long roomId, @Param("accountId") Long accountId);
}

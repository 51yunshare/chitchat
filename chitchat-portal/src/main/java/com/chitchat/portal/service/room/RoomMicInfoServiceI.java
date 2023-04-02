package com.chitchat.portal.service.room;

import com.chitchat.common.base.BaseServicesI;
import com.chitchat.portal.bean.room.RoomMicInfo;
import com.chitchat.portal.vo.room.RoomMicAccountInfoVO;

import java.util.List;

/**
 * 房间麦位
 *
 * Created by Js on 2022/9/10 .
 **/
public interface RoomMicInfoServiceI extends BaseServicesI<RoomMicInfo> {
    /**
     * 获取房间麦位
     *
     * @param roomId
     * @return
     */
    List<RoomMicInfo> getMicByRoomId(Long roomId);

    /**
     * 通过房间id获取房间麦位用户状态
     *
     * @param roomId
     * @return
     */
    List<RoomMicAccountInfoVO> listAccountMicByRoomId(Long roomId);

    /**
     * 初始化房间麦位
     *
     * @param roomId
     * @param micNum
     */
    void init(Long roomId, Integer micNum);

    /**
     * 查询最小空闲麦位
     *
     * @param roomId
     * @return
     */
    RoomMicInfo getMinFreeMicByRoomId(Long roomId);
}

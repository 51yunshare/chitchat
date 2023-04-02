package com.chitchat.portal.dao.room;

import com.chitchat.common.base.BaseDaoI;
import com.chitchat.portal.bean.room.RoomMicInfo;
import com.chitchat.portal.vo.room.RoomMicAccountInfoVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 房间麦位
 *
 * Created by Js on 2022/9/10 .
 **/
public interface RoomMicInfoDaoI extends BaseDaoI<RoomMicInfo> {

    /**
     * 获取房间麦位
     *
     * @param roomId
     * @return
     */
    List<RoomMicInfo> getMicByRoomId(@Param("roomId") Long roomId);

    /**
     * 通过房间id获取房间麦位用户状态
     *
     * @param roomId
     * @return
     */
    List<RoomMicAccountInfoVO> listAccountMicByRoomId(@Param("roomId") Long roomId);

    /**
     * 初始化房间麦位
     *
     * @param roomId
     * @param micNum
     */
    void init(@Param("roomId") Long roomId, @Param("micNum") Integer micNum);

    /**
     * 查询最小空闲麦位
     *
     * @param roomId
     * @return
     */
    RoomMicInfo getMinFreeMicByRoomId(@Param("roomId") Long roomId);
}

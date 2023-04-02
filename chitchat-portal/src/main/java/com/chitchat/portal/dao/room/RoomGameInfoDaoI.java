package com.chitchat.portal.dao.room;

import com.chitchat.common.base.BaseDaoI;
import com.chitchat.portal.bean.room.RoomGameInfo;
import com.chitchat.portal.dto.room.RoomGameRequestDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RoomGameInfoDaoI extends BaseDaoI<RoomGameInfo> {

    /**
     * 获取游戏房间信息
     *
     * @param dto
     * @return
     */
    List<RoomGameInfo> getList(RoomGameRequestDTO dto);

    /**
     * 通过房间号获取游戏房间信息
     *
     * @param roomNo
     * @return
     */
    RoomGameInfo getByRoomNo(@Param("roomNo") String roomNo);

    /**
     * 获取空闲的游戏房间
     *
     * @param dto
     * @return
     */
    RoomGameInfo getFreeGameInfo(RoomGameRequestDTO dto);
}

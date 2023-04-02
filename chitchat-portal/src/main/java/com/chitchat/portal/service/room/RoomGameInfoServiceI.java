package com.chitchat.portal.service.room;

import com.chitchat.common.base.BaseServicesI;
import com.chitchat.common.base.ResultTemplate;
import com.chitchat.portal.bean.room.RoomGameInfo;
import com.chitchat.portal.dto.room.RoomGameAddRequestDTO;
import com.chitchat.portal.dto.room.RoomGameQuitRequestDTO;
import com.chitchat.portal.dto.room.RoomGameRequestDTO;
import com.chitchat.portal.vo.room.RoomGameInfoVO;

/**
 * Created by Js on 2022/10/29 .
 **/
public interface RoomGameInfoServiceI extends BaseServicesI<RoomGameInfo> {

    /**
     * 参加游戏
     *
     * @param dto
     * @return
     */
    RoomGameInfoVO join(RoomGameRequestDTO dto);

    /**
     * 创建游戏
     *
     * @param dto
     * @return
     */
    RoomGameInfoVO addGameRoom(RoomGameAddRequestDTO dto);

    /**
     * 退出游戏
     *
     * @param dto
     */
    void doQuitGame(RoomGameQuitRequestDTO dto);

    /**
     * 获取游戏规则
     *
     * @return
     */
    ResultTemplate getGameRule();

    /**
     * 获取游戏房间信息
     *
     * @param roomNo
     * @return
     */
    RoomGameInfoVO getGameRoomDetail(String roomNo);
}

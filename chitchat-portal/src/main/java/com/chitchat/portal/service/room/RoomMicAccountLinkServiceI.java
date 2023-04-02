package com.chitchat.portal.service.room;

import com.chitchat.common.base.BaseServicesI;
import com.chitchat.portal.bean.room.RoomMicAccountLink;
import com.chitchat.portal.dto.room.RoomUserPageListRequestDTO;
import com.chitchat.portal.vo.room.RoomAccountLinkVO;

import java.util.List;

/**
 * 房间用户麦位关联
 *
 * Created by Js on 2022/9/10 .
 **/
public interface RoomMicAccountLinkServiceI extends BaseServicesI<RoomMicAccountLink> {
    /**
     * 麦上用户信息
     *
     * @param pageListRequest
     * @return
     */
    List<RoomAccountLinkVO> getList(RoomUserPageListRequestDTO pageListRequest);

    /**
     * 用户id和房间id查询用户上麦信息
     *
     * @param roomId
     * @param accountId
     * @return
     */
    RoomMicAccountLink getByRoomIdAndAccountId(Long roomId, Long accountId);
}

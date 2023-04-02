package com.chitchat.portal.service.room.impl;

import com.chitchat.common.base.BaseServicesImpl;
import com.chitchat.portal.bean.room.RoomMicAccountLink;
import com.chitchat.portal.dao.room.RoomMicAccountLinkDaoI;
import com.chitchat.portal.dto.room.RoomUserPageListRequestDTO;
import com.chitchat.portal.service.room.RoomMicAccountLinkServiceI;
import com.chitchat.portal.vo.room.RoomAccountLinkVO;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 房间用户麦位关系
 *
 * Created by Js on 2022/9/10 .
 **/
@Service
public class RoomMicAccountLinkServiceImpl extends BaseServicesImpl<RoomMicAccountLink, RoomMicAccountLinkDaoI> implements RoomMicAccountLinkServiceI {

    /**
     * 麦上用户信息
     *
     * @param pageListRequest
     * @return
     */
    @Override
    public List<RoomAccountLinkVO> getList(RoomUserPageListRequestDTO pageListRequest) {
        return baseDaoT.getList(pageListRequest);
    }

    /**
     * 用户id和房间id查询用户上麦信息
     *
     * @param roomId
     * @param accountId
     * @return
     */
    @Override
    public RoomMicAccountLink getByRoomIdAndAccountId(Long roomId, Long accountId) {
        return baseDaoT.getByRoomIdAndAccountId(roomId, accountId);
    }
}

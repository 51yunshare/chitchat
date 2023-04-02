package com.chitchat.portal.service.room.impl;

import com.chitchat.common.base.BaseServicesImpl;
import com.chitchat.portal.bean.room.RoomMicInfo;
import com.chitchat.portal.dao.room.RoomMicInfoDaoI;
import com.chitchat.portal.service.room.RoomMicInfoServiceI;
import com.chitchat.portal.vo.room.RoomMicAccountInfoVO;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 房间麦位信息
 *
 * Created by Js on 2022/9/10 .
 **/
@Service
public class RoomMicInfoServiceImpl extends BaseServicesImpl<RoomMicInfo, RoomMicInfoDaoI> implements RoomMicInfoServiceI {

    /**
     * 获取房间麦位
     *
     * @param roomId
     * @return
     */
    @Override
    public List<RoomMicInfo> getMicByRoomId(Long roomId) {
        return baseDaoT.getMicByRoomId(roomId);
    }

    /**
     * 通过房间id获取房间麦位用户状态
     *
     * @param roomId
     * @return
     */
    @Override
    public List<RoomMicAccountInfoVO> listAccountMicByRoomId(Long roomId) {
        return baseDaoT.listAccountMicByRoomId(roomId);
    }

    /**
     * 初始化房间麦位
     *
     * @param roomId
     * @param micNum
     */
    @Override
    public void init(Long roomId, Integer micNum) {
        baseDaoT.init(roomId, micNum);
    }

    /**
     * 查询最小空闲麦位
     *
     * @param roomId
     * @return
     */
    @Override
    public RoomMicInfo getMinFreeMicByRoomId(Long roomId) {
        return baseDaoT.getMinFreeMicByRoomId(roomId);
    }
}

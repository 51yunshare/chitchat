package com.chitchat.portal.service.zego;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chitchat.portal.dto.room.*;
import com.chitchat.portal.enumerate.EnumZegoRequestEvent;

import java.util.Set;

/**
 * Created by Js on 2022/8/16 .
 **/
public interface ZegoServiceI {

    /**
     * 获取房间用户列表
     *
     * @param roomId
     * @return
     */
    JSONArray getRoomUserNumByRoomId(Long roomId);

    /**
     * 房间人员列表
     *
     * @param requestDTO
     * @return
     */
    JSONObject getRoomUserList(RoomUserListRequestDTO requestDTO);

    /**
     * 房间踢人
     *
     * @param requestDTO
     */
    JSONObject doRoomKickOutUser(RoomKickOutUserRequestDTO requestDTO);

    /**
     * 房间推送广播/弹幕消息
     *
     * @param requestDTO
     * @param msgRequestEvent
     * @return
     */
    JSONObject doSendBroadcastMessage(RoomSendBroadcastRequestDTO requestDTO, EnumZegoRequestEvent msgRequestEvent);

    /**
     * 房间推送自定义消息
     *
     * @param requestDTO
     * @return
     */
    JSONObject doSendCustomCommand(RoomSendCustomRequestDTO requestDTO);
    JSONObject doSendCustomCommand(String fromUserId, String roomId, String messageContent, Set<Long> toUserId);

    /**
     * 增加房间流
     *
     * @param requestDTO
     * @return
     */
    JSONObject doAddStream(RoomAddStreamRequestDTO requestDTO);

    /**
     * 删除房间流
     *
     * @param requestDTO
     * @return
     */
    JSONObject doDeleteStream(RoomDeletedStreamRequestDTO requestDTO);

    /**
     * 获取简易流列表
     *
     * @param roomId
     * @return
     */
    JSONObject getSimpleStreamList(Long roomId);

    /**
     * 关闭房间
     *
     * @param roomId
     * @return
     */
    JSONObject doCloseRoom(Long roomId);
}

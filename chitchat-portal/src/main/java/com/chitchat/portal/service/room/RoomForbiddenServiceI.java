package com.chitchat.portal.service.room;

import com.chitchat.common.base.BaseServicesI;
import com.chitchat.common.base.ResultTemplate;
import com.chitchat.portal.bean.room.RoomForbidden;
import com.chitchat.portal.dto.room.RoomForbiddenAddRequestDTO;
import com.chitchat.portal.dto.room.RoomForbiddenPageListRequest;

/**
* 房间禁止用户表 Service
 *
 * Created by Js on 2022/8/26 .
*/
public interface RoomForbiddenServiceI extends BaseServicesI<RoomForbidden> {

    /**
     * 获取禁止用户列表
     *
     * @param pageListRequest
     * @return
     */
    ResultTemplate getList(RoomForbiddenPageListRequest pageListRequest);

    /**
     * 房间禁用户
     *
     * @param roomDTO
     */
    void doForbidUser(RoomForbiddenAddRequestDTO roomDTO);

    /**
     * 房间移除被禁用户
     *
     * @param roomId
     * @param id
     */
    void doRemoveForbidUser(Long roomId, Long id);

    /**
     * 查询禁止房间用户信息通过用户Id和房间id
     *
     * @param roomId
     * @param accountId
     * @return
     */
    RoomForbidden getByRoomIdAndAccountId(Long roomId, Long accountId);
}

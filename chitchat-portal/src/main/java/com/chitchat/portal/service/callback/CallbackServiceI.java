package com.chitchat.portal.service.callback;

import com.chitchat.portal.dto.room.RoomLoginCallbackDTO;
import com.chitchat.portal.dto.room.RoomLogoutCallbackDTO;

/**
 * 回调service
 */
public interface CallbackServiceI {


    /**
     * 登录房间回调
     *
     * @param roomVO
     */
    void loginRoomCallback(RoomLoginCallbackDTO roomVO);

    /**
     * 登出房间回调
     *
     * @param roomVO
     */
    void logoutRoomCallback(RoomLogoutCallbackDTO roomVO);
}

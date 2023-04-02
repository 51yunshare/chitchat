package com.chitchat.portal.controller.callback;

import com.chitchat.common.base.BaseController;
import com.chitchat.common.base.ResultTemplate;
import com.chitchat.portal.dto.room.RoomLoginCallbackDTO;
import com.chitchat.portal.dto.room.RoomLogoutCallbackDTO;
import com.chitchat.portal.service.callback.CallbackServiceI;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Js on 2022/8/6.
 **/
@RestController
@Slf4j
@Api(value = "CallbackController", tags = "回调管理")
public class CallbackController extends BaseController {

    @Autowired
    private CallbackServiceI callbackServiceI;

    /**
     *
     * 登录登出回调乱序解决方案
     * https://doc-zh.zego.im/article/15705
     *
     * /

    /**
     * 登录房间回调
     *
     * https://doc-zh.zego.im/article/13766
     *
     * @return
     */
    @ApiOperation(value = "用户登录房间回调", hidden = true)
    @PostMapping(value = "/login")
    public ResultTemplate loginRoomCallback(@RequestBody RoomLoginCallbackDTO roomVO) {
        callbackServiceI.loginRoomCallback(roomVO);
        return this.success();
    }

    /**
     * 登出房间回调
     *
     * https://doc-zh.zego.im/article/13767
     *
     * @return
     */
    @ApiOperation(value = "用户登出房间回调", hidden = true)
    @PostMapping(value = "/logout")
    public ResultTemplate logoutRoomCallback(@RequestBody RoomLogoutCallbackDTO roomVO) {
        callbackServiceI.logoutRoomCallback(roomVO);
        return this.success();
    }
}

package com.chitchat.portal.controller.room;

import com.chitchat.common.base.BaseController;
import com.chitchat.common.base.ResultTemplate;
import com.chitchat.portal.dto.room.*;
import com.chitchat.portal.service.room.RoomAccountLinkServiceI;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 房主管理员操作
 *
 * Created by Js on 2022/9/17.
 **/
@RestController
@RequestMapping("room/admin")
@Api(value = "RoomAdminController", tags = "管理员操作房间")
public class RoomAdminController extends BaseController {
    /*************** 踢人？下麦？邀人上麦？锁麦/解锁？静音 换麦 *****************/
    @Resource
    private RoomAccountLinkServiceI roomAccountLinkServiceI;


    /**
     * 强制下麦
     *
     * @return
     */
    @ApiOperation(value = "管理员强制下麦")
    @PostMapping(value = "/offMic")
    public ResultTemplate doAdminOffMic(@RequestBody @Validated RoomAdminMicRequestDTO requestDTO) {
        roomAccountLinkServiceI.doAdminOffMic(requestDTO);
        return this.success();
    }

    /**
     * 管理员锁麦/解麦操作
     *
     * @param requestDTO
     * @return
     */
    @ApiOperation(value = "管理员锁麦/解麦操作")
    @PostMapping(value = "/lockMic")
    public ResultTemplate doAdminLockMic(@RequestBody @Validated RoomAdminLockMicRequestDTO requestDTO) {
        roomAccountLinkServiceI.doAdminLockMic(requestDTO);
        return this.success();
    }
    /**
     * 管理员操作用户麦位是否静音
     *
     * @param requestDTO
     * @return
     */
    @ApiOperation(value = "管理员操作用户麦位是否静音")
    @PostMapping(value = "/muteMic")
    public ResultTemplate doAdminMuteMic(@RequestBody @Validated RoomAdminMuteMicRequestDTO requestDTO) {
        roomAccountLinkServiceI.doAdminMuteMic(requestDTO);
        return this.success();
    }

    /**
     * 管理员踢人操作
     *
     * @param requestDTO
     * @return
     */
    @ApiOperation(value = "管理员踢人操作")
    @PostMapping(value = "/kickOut")
    public ResultTemplate doAdminKickOut(@RequestBody @Validated RoomAdminKickOutRequestDTO requestDTO) {
        roomAccountLinkServiceI.doAdminKickOut(requestDTO);
        return this.success();
    }
    /**
     * 管理员邀人上麦-发送消息
     *
     * @param requestDTO
     * @return
     */
    @ApiOperation(value = "管理员邀人上麦操作")
    @PostMapping(value = "/inviteMic")
    public ResultTemplate doAdminInviteMic(@RequestBody @Validated RoomAdminInviteMicRequestDTO requestDTO) {
        roomAccountLinkServiceI.doAdminInviteMic(requestDTO);
        return this.success();
    }

    /**
     * 管理员换麦
     *
     * @return
     */
    @ApiOperation(value = "管理员换麦")
    @PostMapping(value = "/switchMic")
    public ResultTemplate doAdminSwitchMic(@RequestBody @Validated RoomAdminSwitchMicRequestDTO requestDTO) {
        roomAccountLinkServiceI.doAdminSwitchMic(requestDTO);
        return this.success();
    }
}

package com.chitchat.portal.controller.room;

import com.chitchat.common.base.BaseController;
import com.chitchat.common.base.ResultTemplate;
import com.chitchat.portal.dto.room.RoomForbiddenAddRequestDTO;
import com.chitchat.portal.dto.room.RoomForbiddenPageListRequest;
import com.chitchat.portal.service.room.RoomForbiddenServiceI;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 房间禁止用户
 *
 * Created by Js on 2022/8/26 .
 **/
@RestController
@RequestMapping("room/forbid")
@Api(value = "RoomForbiddenController", tags = "房间禁止用户管理")
@Slf4j
public class RoomForbiddenController extends BaseController {
    /** 禁止用户列表、禁用户操作、移除被禁止的用户 **/

    @Autowired
    private RoomForbiddenServiceI roomForbiddenServiceI;

    /**
     * 获取禁止用户列表
     *
     * @param pageListRequest
     * @return
     */
    @ApiOperation("获取禁止用户列表")
    @ApiOperationSupport(ignoreParameters = {"userInfo"})
    @GetMapping(value = "/list")
    public ResultTemplate list(RoomForbiddenPageListRequest pageListRequest) {
        return roomForbiddenServiceI.getList(pageListRequest);
    }

    /**
     * 房间禁用户
     *
     * @param roomDTO
     * @return
     */
    @ApiOperation(value = "房间禁用户")
    @PostMapping("/forbidUser")
    public ResultTemplate doForbidUser(@RequestBody @Validated RoomForbiddenAddRequestDTO roomDTO) {
        roomForbiddenServiceI.doForbidUser(roomDTO);
        return this.success();
    }

    /**
     * 房间移除被禁用户
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "房间移除被禁用户")
    @PostMapping("/remove/{roomId}/{id}")
    public ResultTemplate doRemoveForbidUser(@PathVariable Long roomId, @PathVariable Long id) {
        roomForbiddenServiceI.doRemoveForbidUser(roomId, id);
        return this.success();
    }
}

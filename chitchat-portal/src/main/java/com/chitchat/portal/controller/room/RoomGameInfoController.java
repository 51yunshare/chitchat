package com.chitchat.portal.controller.room;

import com.chitchat.common.base.BaseController;
import com.chitchat.common.base.Result;
import com.chitchat.common.base.ResultTemplate;
import com.chitchat.portal.dto.room.RoomGameAddRequestDTO;
import com.chitchat.portal.dto.room.RoomGameQuitRequestDTO;
import com.chitchat.portal.dto.room.RoomGameRequestDTO;
import com.chitchat.portal.service.room.RoomGameInfoServiceI;
import com.chitchat.portal.vo.room.RoomGameInfoVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 游戏房间
 *
 * Created by Js on 2022/10/29 .
 **/
@RestController
@Slf4j
@RequestMapping("room/game")
@Api(value = "RoomGameInfoController", tags = "游戏房间管理")
@RequiredArgsConstructor
public class RoomGameInfoController extends BaseController {

    private final RoomGameInfoServiceI roomGameInfoServiceI;

    /**
     * 参加游戏
     * 1.球赛制: 随机选取对应房间，如果没有后面创建
     * 2.slot: 下发对应规则
     * 3.返回对应房间信息/或者没有对应房间信息
     *
     * @return
     */
    @ApiOperation("参加游戏")
    @PostMapping(value = "/join")
    public Result<RoomGameInfoVO> join(@RequestBody @Validated RoomGameRequestDTO dto) {
        return Result.success(roomGameInfoServiceI.join(dto));
    }

    @ApiOperation("获取游戏房间信息")
    @PostMapping(value = "/detail")
    public Result<RoomGameInfoVO> getGameRoomDetail(@RequestParam(value = "roomNo", required = false) String roomNo) {
        return Result.success(roomGameInfoServiceI.getGameRoomDetail(roomNo));
    }

    /**
     * 创建游戏
     *
     * @return
     */
    @ApiOperation(value = "创建游戏", hidden = true)
    @PostMapping(value = "/add")
    public Result<RoomGameInfoVO> addGameRoom(@RequestBody @Validated RoomGameAddRequestDTO dto) {
        return Result.success(roomGameInfoServiceI.addGameRoom(dto));
    }

    /**
     * 退出游戏
     *
     * @return
     */
    @ApiOperation("退出游戏")
    @PostMapping(value = "/quitGame")
    public ResultTemplate doQuitGame(@RequestBody @Validated RoomGameQuitRequestDTO dto) {
        roomGameInfoServiceI.doQuitGame(dto);
        return success();
    }

    /**
     * 获取游戏规则
     *
     * @return
     */
    @ApiOperation("获取游戏规则")
    @GetMapping(value = "/getRule")
    public ResultTemplate getGameRule() {
        return roomGameInfoServiceI.getGameRule();
    }
}

package com.chitchat.portal.controller.room;

import com.chitchat.common.base.BaseController;
import com.chitchat.common.base.Result;
import com.chitchat.common.base.ResultTemplate;
import com.chitchat.portal.dto.room.RoomAddRequestDTO;
import com.chitchat.portal.dto.room.RoomPageListRequest;
import com.chitchat.portal.dto.room.RoomUpdateLockRequestDTO;
import com.chitchat.portal.dto.room.RoomUpdateRequestDTO;
import com.chitchat.portal.service.room.RoomServiceI;
import com.chitchat.portal.vo.room.MyRoomPageListVO;
import com.chitchat.portal.vo.room.RoomDetailVO;
import com.chitchat.portal.vo.room.RoomInfoVO;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Js on 2022/7/29 .
 **/
@RestController
@Slf4j
@RequestMapping("room")
@Api(value = "RoomController", tags = "会员房间管理")
public class RoomController extends BaseController {

    @Autowired
    private RoomServiceI roomServiceI;

    /**
     * 所有房间列表
     *
     * @param pageListRequest
     * @return
     */
    @ApiOperation("获取所有房间列表")
    @ApiOperationSupport(ignoreParameters = {"userInfo"})
    @GetMapping(value = "/list")
    public ResultTemplate list(RoomPageListRequest pageListRequest) {
        return roomServiceI.getList(pageListRequest);
    }

    /**
     * 我的房间信息
     *
     * @return
     */
    @ApiOperation("我的房间信息")
    @GetMapping(value = "/myRoomInfo")
    public ResultTemplate getMyRoomInfo() {
        return this.success(roomServiceI.getMyRoomInfo());
    }

    /**
     * 最近我加入房间列表
     *
     * @param pageListRequest
     * @return
     */
    @ApiOperation(value = "获取最近我加入的房间列表", response = MyRoomPageListVO.class)
    @ApiOperationSupport(ignoreParameters = {"userInfo"})
    @GetMapping(value = "/myList")
    public ResultTemplate myList(RoomPageListRequest pageListRequest) {
        return roomServiceI.getMyJoinRoomList(pageListRequest);
    }

    /**
     * 我关注的
     *
     * @param pageListRequest
     * @return
     */
    @ApiOperation("我关注的所有房间列表")
    @ApiOperationSupport(ignoreParameters = {"userInfo"})
    @GetMapping(value = "/myFollowingRoom")
    public ResultTemplate myFollowingList(RoomPageListRequest pageListRequest) {
        return roomServiceI.myFollowingList(pageListRequest);
    }

    /**
     * 新建房间
     *
     * @param roomDTO
     * @return
     */
    @ApiOperation(value = "新建房间",response = RoomInfoVO.class)
    @PostMapping("/addRoom")
    public ResultTemplate doAddRoom(@Validated @ApiParam("新建房间请求体") RoomAddRequestDTO roomDTO) {
        return this.success(roomServiceI.doAddRoom(roomDTO));
    }

    /**
     * 获取房间信息
     *
     * @return
     */
    @ApiOperation(value = "房间详情")
    @GetMapping(value = "/detail")
    public Result<RoomDetailVO> getDetail(@RequestParam Long id) {
        return Result.success(roomServiceI.getDetail(id));
    }

    /**
     * 房间编辑
     *
     * @param roomDTO
     * @return
     */
    @ApiOperation(value = "编辑房间")
    @PostMapping("/updateRoom")
    public ResultTemplate doUpdateRoom(@Validated RoomUpdateRequestDTO roomDTO) {
        roomServiceI.doUpdateRoom(roomDTO);
        return this.success();
    }

    @ApiOperation(value = "房间上/解锁")
    @PostMapping("/lockRoom")
    public ResultTemplate doLockRoom(@RequestBody @Validated RoomUpdateLockRequestDTO roomDTO) {
        roomServiceI.doLockRoom(roomDTO);
        return this.success();
    }

    /**
     * 销毁房间
     *
     * @param roomId
     * @return
     */
    @ApiOperation(value = "销毁房间")
    @DeleteMapping("/destroyRoom/{roomId}")
    public ResultTemplate doDestroyRoom(@ApiParam("房间ID") @PathVariable Long roomId) {
        roomServiceI.doDestroyRoom(roomId);
        return this.success();
    }

    //popular
    /**
     * 我关注和我加入的房间列表为空，需要获取推荐列表
     *
     * @param pageListRequest
     * @return
     */
    @ApiOperation("推荐房间列表")
    @ApiOperationSupport(ignoreParameters = {"userInfo"})
    @GetMapping(value = "/popularRoom")
    public ResultTemplate getPopularRoomList(RoomPageListRequest pageListRequest) {
        return roomServiceI.getPopularRoomList(pageListRequest);
    }

    /**
     * 房间类型
     *
     * @return
     */
    @ApiOperation(value = "房间类型")
    @GetMapping("/roomType")
    public ResultTemplate getRoomType() {
        return this.success(roomServiceI.getRoomType());
    }


}

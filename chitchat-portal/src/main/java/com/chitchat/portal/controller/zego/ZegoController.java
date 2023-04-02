package com.chitchat.portal.controller.zego;

import com.chitchat.common.base.BaseController;
import com.chitchat.common.base.ResultTemplate;
import com.chitchat.common.web.jwt.UserUtils;
import com.chitchat.portal.config.ZegoConfigProperties;
import com.chitchat.portal.dto.room.*;
import com.chitchat.portal.service.room.RoomServiceI;
import com.chitchat.portal.zego.ZegoGenerateToken;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Js on 2022/8/17.
 **/
@RestController
@Slf4j
@RequestMapping("zego")
@Api(value = "ZegoController", tags = "即构服务端管理")
public class ZegoController extends BaseController {

    @Autowired
    private ZegoConfigProperties zegoConfigProperties;
    @Autowired
    private RoomServiceI roomServiceI;

    /**
     * 服务器生成Token
     *
     * https://doc-zh.zego.im/article/14046
     *
     * @return
     */
    @ApiOperation("用户登录房间生成Token")
    @GetMapping(value = "/token")
    public ResultTemplate generateToken() {
        return this.success(ZegoGenerateToken.generateBaseToken(zegoConfigProperties.getAppId(), zegoConfigProperties.getServerSecret(), String.valueOf(UserUtils.getUserId())));
    }
    /**
     * 房间人员数量
     *
     * @param roomId
     * @return
     */
    @ApiOperation(value = "获取房间人员数量")
    @GetMapping("/getRoomUserNum/{roomId}")
    public ResultTemplate getRoomUserNumList(@ApiParam("房间ID") @PathVariable Long roomId) {
        return roomServiceI.getRoomUserNumList(roomId);
    }

    /**
     * 房间人员列表
     *
     * @param requestDTO
     * @return
     */
    @ApiOperation(value = "获取房间人员列表")
    @PostMapping("/getRoomUser")
    public ResultTemplate getRoomUserList(@RequestBody @Validated RoomUserListRequestDTO requestDTO) {
        return roomServiceI.getRoomUserList(requestDTO);
    }

    /**
     * 房间踢人
     *
     * @param requestDTO
     * @return
     */
    @ApiOperation(value = "房间踢人操作")
    @PostMapping("/kickUser")
    public ResultTemplate doRoomKickOutUser(@RequestBody @Validated RoomKickOutUserRequestDTO requestDTO) {
        roomServiceI.doRoomKickOutUser(requestDTO);
        return this.success();
    }

    /**
     * 房间推送广播
     *
     * 推送广播消息，即向同一房间内所有用户发送文本消息
     *
     * 注意：用户客户端如果使用上述 UserId 登录房间，而服务端使用该 UserId 发送请求时，会导致该客户端无法收到服务端消息？？？
     *
     * @param requestDTO
     * @return
     */
    @ApiOperation(value = "房间推送广播消息")
    @PostMapping("/sendBroadcast")
    public ResultTemplate doSendBroadcastMessage(@RequestBody @Validated RoomSendBroadcastRequestDTO requestDTO) {
        return this.success(roomServiceI.doSendBroadcastMessage(requestDTO));
    }

    /**
     * 房间推送弹幕
     *
     * 推送弹幕消息，一般用于房间内有大量消息收发且不需要保证消息可靠性的场景
     *
     * 注意：用户客户端如果使用上述 UserId 登录房间，而服务端使用该 UserId 发送请求时，会导致该客户端无法收到服务端消息？？？
     *
     *
     * @param requestDTO
     * @return
     */
    @ApiOperation(value = "房间推送弹幕消息")
    @PostMapping("/sendBarrage")
    public ResultTemplate doSendBarrageMessage(@RequestBody @Validated RoomSendBroadcastRequestDTO requestDTO) {
        return this.success(roomServiceI.doSendBarrageMessage(requestDTO));
    }

    /**
     * 房间推送自定义消息
     *
     * 推送自定义消息，即向同一房间内指定的单个或多个用户发送信令消息。
     *
     * 注意：用户客户端如果使用上述 UserId 登录房间，而服务端使用该 UserId 发送请求时，会导致该客户端无法收到服务端消息？？？
     *
     * @param requestDTO
     * @return
     */
    @ApiOperation(value = "房间推送自定义消息")
    @PostMapping("/sendCustomCommand")
    public ResultTemplate doSendCustomCommand(@RequestBody @Validated RoomSendCustomRequestDTO requestDTO) {
        return this.success(roomServiceI.doSendCustomCommand(requestDTO));
    }

    /**
     * 增加房间流
     *
     * 往指定房间增加流时需要保证该房间是存在的，本接口无法创建房间。
     * 本接口一般与 删除房间流 配合使用，比如业务上需要停止向某个房间广播流
     *
     * 调用本服务端接口时：
     *
     * 不建议使用与房间内实际用户相同的 UserId，避免与客户端 SDK 的流新增行为产生冲突。可以使用特定的名称标识为服务端行为，例如：userId = “Server-Administrator”。
     * 如果使用了与房间内实际用户相同的 UserId（不建议）时，需要注意以下事项：
     *      相应操作人 UserId 的客户端不会收到本服务端接口触发的流增加回调。
     *      如果实际房间内用户 UserId 没有推流，会触发相应 StreamId 的流删除逻辑。
     *      如果实际房间内用户 UserId 退出房间，会触发相应 StreamId 的流删除逻辑。
     *
     * @param requestDTO
     * @return
     */
    @ApiOperation(value = "增加房间流")
    @PostMapping("/addStream")
    public ResultTemplate doAddStream(@RequestBody @Validated RoomAddStreamRequestDTO requestDTO) {
        roomServiceI.doAddStream(requestDTO);
        return this.success();
    }

    /**
     * 删除房间流
     * 调用本接口删除某个房间的指定流，与 踢出房间用户 不同的是，这路流对应的用户是仍在房间内的。可通过本接口强制“下麦”某个用户，或者在“一起看”场景里删掉增加的 CDN 直播流。
     * 本接口仅用于抛出房间内流删除的相关通知，不会执行实际的停止推流操作
     *
     * 调用本服务端接口时：
     * 不建议使用与房间内实际用户相同的 UserId，避免与客户端 SDK 的流删除行为产生冲突。可以使用特定的名称标识为服务端行为，例如：userId = “Server-Administrator”。
     * 如果使用了与房间内实际用户相同的 UserId（不建议）时，相应操作人 UserId 的客户端不会收到本服务端接口触发的流删除回调。
     *
     * @param requestDTO
     * @return
     */
    @ApiOperation(value = "删除房间流")
    @PostMapping("/deleteStream")
    public ResultTemplate doDeleteStream(@RequestBody @Validated RoomDeletedStreamRequestDTO requestDTO) {
        roomServiceI.doDeleteStream(requestDTO);
        return this.success();
    }

    /**
     * 获取简易流列表
     *
     * 调用本接口获取房间内流列表信息。获取流列表后与业务后台流列表进行对比，可以防止“炸麦”。也可以通过此接口获取房间内唯一流 ID，在使用第三方拉流器时进行混流音浪回调。
     *
     * @param roomId
     * @return
     */
    @ApiOperation(value = "获取简易流列表")
    @GetMapping("/simpleStreamList/{roomId}")
    public ResultTemplate getSimpleStreamList(@ApiParam("房间ID") @PathVariable Long roomId) {
        return roomServiceI.getSimpleStreamList(roomId);
    }

    /**
     * 关闭房间
     *
     * 调用本接口将把房间内所有用户从房间移出，并关闭房间
     *
     * @param roomId
     * @return
     */
    @ApiOperation(value = "关闭房间")
    @GetMapping("/closeRoom/{roomId}")
    public ResultTemplate doCloseRoom(@ApiParam("房间ID") @PathVariable Long roomId) {
        roomServiceI.doCloseRoom(roomId);
        return this.success();
    }
}

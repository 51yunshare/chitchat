package com.chitchat.portal.controller.room;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.chitchat.common.base.BaseController;
import com.chitchat.common.base.Result;
import com.chitchat.common.base.ResultTemplate;
import com.chitchat.common.constant.NotifyConstant;
import com.chitchat.common.constant.SystemConstants;
import com.chitchat.common.redis.RedisKey;
import com.chitchat.common.redis.RedisTemplateStringUtil;
import com.chitchat.common.util.JSONObjectUtil;
import com.chitchat.portal.bean.room.RoomInfo;
import com.chitchat.portal.dto.base.GiftSendRequestDTO;
import com.chitchat.portal.dto.room.*;
import com.chitchat.portal.redis.NotifyMessage;
import com.chitchat.portal.service.room.RoomAccountLinkServiceI;
import com.chitchat.portal.vo.room.RoomAccountLinkVO;
import com.chitchat.portal.vo.room.RoomContributionRankVO;
import com.chitchat.portal.vo.room.RoomUserBaseInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 房间用户管理
 *
 * Created by Js on 2022/8/26 .
 **/
@RestController
@RequestMapping("room/user")
@Api(value = "RoomUserController", tags = "房间用户管理")
@Slf4j
public class RoomAccountController extends BaseController {
    /** 加入房间、用户角色修改 **/

    @Autowired
    private RoomAccountLinkServiceI roomAccountLinkServiceI;
    @Autowired
    private RedisTemplateStringUtil redisTemplateStringUtil;

    /**
     * 房间在线用户列表
     *
     * @param listRequestDTO
     * @return
     */
    @ApiOperation(value = "房间在线用户列表", response = RoomAccountLinkVO.class)
    @GetMapping(value = "/list")
    public ResultTemplate listRoomUser(@Validated RoomOnlineUserListRequestDTO listRequestDTO) {
        return this.success(roomAccountLinkServiceI.listOnlineRoomUser(listRequestDTO));
    }
    /*public ResultTemplate listRoomUser(@Validated RoomUserPageListRequestDTO pageListRequest) {
        return roomAccountLinkServiceI.listRoomUser(pageListRequest);
    }*/

    /**
     * 获取会员信息
     *
     * @return
     */
    @ApiOperation(value = "获取房间会员信息",response = RoomUserBaseInfo.class)
    @GetMapping(value = "/getAccountInfo")
    public ResultTemplate getAccountInfo(@RequestParam("accountId") Long accountId, @RequestParam("roomId") Long roomId) {
        return roomAccountLinkServiceI.getAccountInfo(accountId, roomId);
    }

    /**
     * 观众进入房间
     *
     * @param dto
     * @return
     */
    @ApiOperation(value = "观众进入房间")
    @PostMapping("/entryRoom")
    public ResultTemplate entryRoom(@RequestBody @Validated AccountEntryRoomRequestDTO dto) {
        RoomInfo roomInfo = roomAccountLinkServiceI.entryRoom(dto);

        redisTemplateStringUtil.send(NotifyConstant.REDIS_CHANNEL, JSON.toJSONString(NotifyMessage.builder()
                .fromUserId(SystemConstants.SERVER_USER_ID)
                .targetId(dto.getRoomId().toString())
                .content(new JSONObject()
                        .fluentPut("hotNum", redisTemplateStringUtil.getKey(RedisKey.getRoomHotNum(dto.getRoomId())) == null ? 0 : redisTemplateStringUtil.getKey(RedisKey.getRoomHotNum(dto.getRoomId())))
                        .fluentPut("onlineNum", redisTemplateStringUtil.getKey(RedisKey.getRoomOnlineNum(dto.getRoomId()))  == null ? 0 : redisTemplateStringUtil.getKey(RedisKey.getRoomOnlineNum(dto.getRoomId()))))
                .build()));
        return this.success(JSONObjectUtil.parseObject(roomInfo));
    }

    /**
     * 观众退出房间
     *
     * @param roomId
     * @return
     */
    @ApiOperation(value = "观众退出房间")
    @PostMapping("/quitRoom/{roomId}")
    public ResultTemplate quitRoom(@PathVariable Long roomId) {
        roomAccountLinkServiceI.quitRoom(roomId);
        return this.success();
    }

    /**
     * 房主设置管理员
     *
     * @param dto
     * @return
     */
    @ApiOperation(value = "设置管理员")
    @PostMapping("/setRole")
    public ResultTemplate doSetRole(@RequestBody @Validated RoomUserRoleRequestDTO dto) {
        roomAccountLinkServiceI.doSetRole(dto);
        return this.success();
    }

    /**
     * 取消管理员
     *
     * @param dto
     * @return
     */
    @ApiOperation(value = "取消管理员")
    @PostMapping("/cancelRole")
    public ResultTemplate doCancelRole(@RequestBody @Validated RoomUserRoleRequestDTO dto) {
        roomAccountLinkServiceI.doCancelRole(dto);
        return this.success();
    }

    /**
     * 用户上麦
     *
     * @param dto
     * @return
     */
    @ApiOperation(value = "用户上麦")
    @PostMapping("/upMic")
    public ResultTemplate accountUpMic(@RequestBody @Validated RoomUserUpMicRequestDTO dto){
        return this.success(roomAccountLinkServiceI.accountUpMic(dto));
    }

    /**
     * 邀请用户上麦
     *
     * @param dto
     * @return
     */
    /*@ApiOperation(value = "邀请用户上麦")
    @PostMapping("/inviteUpMic")
    public ResultTemplate accountInviteUpMic(@RequestBody @Validated RoomUserInviteUpMicRequestDTO dto){
        roomAccountLinkServiceI.accountInviteUpMic(dto);
        return this.success();
    }*/

    @ApiOperation(value = "被邀请用户同意上麦")
    @PostMapping("/agreeUpMic")
    public ResultTemplate accountAgreeUpMic(@RequestBody @Validated RoomUserAgreeUpMicRequestDTO dto){
        return this.success(roomAccountLinkServiceI.accountAgreeUpMic(dto));
    }

    /**
     * 用户闭麦
     *
     * @param dto
     * @return
     */
    @ApiOperation(value = "用户闭麦")
    @PostMapping("/closeMic")
    public ResultTemplate accountClosedMic(@RequestBody @Validated RoomUserOffMicRequestDTO dto){
        roomAccountLinkServiceI.accountClosedMic(dto);
        return this.success();
    }

    /**
     * 用户开麦
     *
     * @param dto
     * @return
     */
    @ApiOperation(value = "用户开麦")
    @PostMapping("/openMic")
    public ResultTemplate accountOpenMic(@RequestBody @Validated RoomUserOffMicRequestDTO dto){
        roomAccountLinkServiceI.accountOpenMic(dto);
        return this.success();
    }

    /**
     * 用户下麦
     *
     * @param dto
     * @return
     */
    @ApiOperation(value = "用户下麦")
    @PostMapping("/offMic")
    public ResultTemplate accountOffMic(@RequestBody @Validated RoomUserOffMicRequestDTO dto){
        roomAccountLinkServiceI.accountOffMic(dto);
        return this.success();
    }

    /**
     * 发送礼物
     *
     * @param dto
     * @return
     */
    @ApiOperation("发送礼物")
    @PostMapping("/send")
    public ResultTemplate doSendGift(@RequestBody @Validated GiftSendRequestDTO dto) {
        roomAccountLinkServiceI.doSendGift(dto);
        return this.success();
    }

    /**
     * 房间贡献值排行榜
     *
     * @param roomId
     * @return
     */
    @ApiOperation(value = "房间贡献值排行榜",response = RoomContributionRankVO.class)
    @GetMapping("/contributionRank/{roomId}")
    public Result getContributionRank(@ApiParam(value = "房间id") @PathVariable Long roomId,
                                              @ApiParam(value = "房间榜单类型，1-日榜 2-周榜") @RequestParam Integer type) {
        List<RoomContributionRankVO> voList = roomAccountLinkServiceI.getContributionRank(roomId, type);
        return Result.success(voList);
    }

}

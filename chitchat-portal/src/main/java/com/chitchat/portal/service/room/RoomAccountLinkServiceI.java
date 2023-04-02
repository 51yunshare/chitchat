package com.chitchat.portal.service.room;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chitchat.common.base.BaseServicesI;
import com.chitchat.common.base.ResultTemplate;
import com.chitchat.portal.bean.room.RoomAccountLink;
import com.chitchat.portal.bean.room.RoomInfo;
import com.chitchat.portal.dto.base.GiftSendRequestDTO;
import com.chitchat.portal.dto.room.*;
import com.chitchat.portal.vo.room.RoomContributionRankVO;

import java.util.List;

public interface RoomAccountLinkServiceI extends BaseServicesI<RoomAccountLink> {

    /**
     * 查询房间在线用户数量
     *
     * @param roomId
     * @return
     */
    int getUserNumByRoomId(Long roomId);

    /**
     * 通过房间id和用户id查询关联关系
     *
     * @param roomId
     * @param accountId
     * @return
     */
    RoomAccountLink getByRoomIdAndAccountId(Long roomId, Long accountId);

    /**
     * 设置管理员
     *
     * @param dto
     */
    void doSetRole(RoomUserRoleRequestDTO dto);

    /**
     * 取消管理员
     *
     * @param dto
     */
    void doCancelRole(RoomUserRoleRequestDTO dto);

    /**
     * 进入房间
     *
     * @param dto
     */
    RoomInfo entryRoom(AccountEntryRoomRequestDTO dto);

    /**
     * 观众退出房间
     *
     * @param roomId
     * @return
     */
    void quitRoom(Long roomId);

    /**
     * 用户上麦
     *
     * @param dto
     */
    JSONObject accountUpMic(RoomUserUpMicRequestDTO dto);

    /**
     * 用户下麦
     *
     * @param dto
     */
    void accountOffMic(RoomUserOffMicRequestDTO dto);

    /**
     * 房间在线用户列表
     *
     * @param pageListRequest
     * @return
     */
    ResultTemplate listRoomUser(RoomUserPageListRequestDTO pageListRequest);

    /**
     * 房间在线用户列表-无分页
     *
     * @param listRequestDTO
     * @return
     */
    JSONArray listOnlineRoomUser(RoomOnlineUserListRequestDTO listRequestDTO);

    /**
     * 发送礼物
     *
     * @param dto
     */
    void doSendGift(GiftSendRequestDTO dto);

    /**
     * 房间贡献值排行榜
     *
     * @param roomId
     * @param type
     * @return
     */
    List<RoomContributionRankVO> getContributionRank(Long roomId, Integer type);

    /**
     * 管理员锁麦/解麦操作
     *
     * @param requestDTO
     */
    void doAdminLockMic(RoomAdminLockMicRequestDTO requestDTO);

    /**
     * 管理员强制下麦
     *
     * @param requestDTO
     */
    void doAdminOffMic(RoomAdminMicRequestDTO requestDTO);

    /**
     * 管理员操作用户麦位是否静音
     *
     * @param requestDTO
     */
    void doAdminMuteMic(RoomAdminMuteMicRequestDTO requestDTO);

    /**
     * 管理员踢人操作
     *
     * @param requestDTO
     */
    void doAdminKickOut(RoomAdminKickOutRequestDTO requestDTO);

    /**
     * 管理员邀人上麦-发送消息
     *
     * @param requestDTO
     */
    void doAdminInviteMic(RoomAdminInviteMicRequestDTO requestDTO);

    /**
     * 管理员换麦
     *
     * @param requestDTO
     */
    void doAdminSwitchMic(RoomAdminSwitchMicRequestDTO requestDTO);

    /**
     * 用户闭麦
     *
     * @param dto
     */
    void accountClosedMic(RoomUserOffMicRequestDTO dto);

    /**
     * 用户开麦
     *
     * @param dto
     */
    void accountOpenMic(RoomUserOffMicRequestDTO dto);

    /**
     * 获取会员信息
     *
     * @param accountId
     * @param roomId
     * @return
     */
    ResultTemplate getAccountInfo(Long accountId, Long roomId);

    /**
     * 邀请用户上麦
     *
     * @param dto
     */
    void accountInviteUpMic(RoomUserInviteUpMicRequestDTO dto);

    /**
     * 被邀请用户同意上麦
     *
     * @param dto
     */
    JSONObject accountAgreeUpMic(RoomUserAgreeUpMicRequestDTO dto);
}

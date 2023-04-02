package com.chitchat.portal.service.room;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chitchat.common.base.BaseServicesI;
import com.chitchat.common.base.ResultTemplate;
import com.chitchat.portal.bean.room.RoomInfo;
import com.chitchat.portal.dto.index.HomeRankListRequest;
import com.chitchat.portal.dto.index.HomeSearchPageListRequest;
import com.chitchat.portal.dto.room.*;
import com.chitchat.portal.vo.index.RankVO;
import com.chitchat.portal.vo.room.RoomDetailVO;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * Created by Js on 2022/7/29 .
 **/
public interface RoomServiceI extends BaseServicesI<RoomInfo> {

    /**
     * 房间列表-分页
     *
     * @param pageListRequest
     * @return
     */
    ResultTemplate getList(RoomPageListRequest pageListRequest);

    /**
     * 新建房间
     *
     * @param roomDTO
     * @return
     */
    JSONObject doAddRoom(RoomAddRequestDTO roomDTO);

    /**
     * 房间编辑
     *
     * @param roomDTO
     */
    void doUpdateRoom(RoomUpdateRequestDTO roomDTO);

    /**
     * 房间上/解锁
     *
     * @param roomDTO
     */
    void doLockRoom(RoomUpdateLockRequestDTO roomDTO);

    /**
     * 获取房间信息
     *
     * @param id
     * @return
     */
    RoomDetailVO getDetail(Long id);

    /**
     * 销毁房间
     *
     * @param roomId
     */
    void doDestroyRoom(Long roomId);

    /**
     * 获取房间人员
     *
     * @param roomId
     * @return
     */
    ResultTemplate getRoomUserNumList(Long roomId);

    /**
     * 房间人员列表
     *
     * @param requestDTO
     * @return
     */
    ResultTemplate getRoomUserList(RoomUserListRequestDTO requestDTO);

    /**
     * 房间踢人操作
     *
     * @param requestDTO
     * @return
     */
    void doRoomKickOutUser(RoomKickOutUserRequestDTO requestDTO);

    /**
     * 房间推送广播消息
     *
     * @param requestDTO
     */
    JSONObject doSendBroadcastMessage(RoomSendBroadcastRequestDTO requestDTO);

    /**
     * 房间推送弹幕消息
     *
     * @param requestDTO
     */
    JSONObject doSendBarrageMessage(RoomSendBroadcastRequestDTO requestDTO);

    /**
     * 房间推送自定义消息
     *
     * @param requestDTO
     */
    JSONObject doSendCustomCommand(RoomSendCustomRequestDTO requestDTO);

    /**
     * 最近我加入房间列表
     *
     * @param pageListRequest
     * @return
     */
    ResultTemplate getMyJoinRoomList(RoomPageListRequest pageListRequest);

    /**
     * 我关注的房间列表
     *
     * @param pageListRequest
     * @return
     */
    ResultTemplate myFollowingList(RoomPageListRequest pageListRequest);

    /**
     * 搜索
     *
     * @param pageListRequest
     * @return
     */
    PageInfo selectBySearch(HomeSearchPageListRequest pageListRequest);

    /**
     * 增加房间流
     *
     * @param requestDTO
     */
    void doAddStream(RoomAddStreamRequestDTO requestDTO);

    /**
     * 删除房间流
     *
     * @param requestDTO
     */
    void doDeleteStream(RoomDeletedStreamRequestDTO requestDTO);

    /**
     * 获取简易流列表
     *
     * @param roomId
     * @return
     */
    ResultTemplate getSimpleStreamList(Long roomId);

    /**
     * 关闭房间
     *
     * @param roomId
     */
    void doCloseRoom(Long roomId);

    /**
     * 推荐房间列表
     *
     * @param pageListRequest
     * @return
     */
    ResultTemplate getPopularRoomList(RoomPageListRequest pageListRequest);

    /**
     * 修改房间用户人数
     *
     * @param roomId
     */
    void updateRoomUserNum(Long roomId);

    /**
     * 我的房间信息
     *
     * @return
     */
    JSONObject getMyRoomInfo();

    /**
     * 获取房间类型
     *
     * @return
     */
    JSONArray getRoomType();

    /**
     * 排行榜
     *
     * @param listRequest
     * @return
     */
    List<RankVO> listRank(HomeRankListRequest listRequest);
}

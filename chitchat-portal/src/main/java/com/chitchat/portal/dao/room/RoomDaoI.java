package com.chitchat.portal.dao.room;

import com.chitchat.common.base.BaseDaoI;
import com.chitchat.portal.bean.room.RoomInfo;
import com.chitchat.portal.dto.index.HomeRankListRequest;
import com.chitchat.portal.dto.index.HomeSearchPageListRequest;
import com.chitchat.portal.dto.room.RoomPageListRequest;
import com.chitchat.portal.vo.index.RankVO;
import com.chitchat.portal.vo.room.MyRoomPageListVO;
import com.chitchat.portal.vo.index.SearchRoomVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by Js on 2022/8/3 .
 **/
public interface RoomDaoI extends BaseDaoI<RoomInfo> {

    /**
     * 最近我加入房间列表
     *
     * @param pageListRequest
     * @return
     */
    List<MyRoomPageListVO> getMyJoinRoomList(RoomPageListRequest pageListRequest);

    /**
     * 我关注的房间列表
     *
     * @param pageListRequest
     * @return
     */
    List<MyRoomPageListVO> getMyFollowingList(RoomPageListRequest pageListRequest);

    /**
     * 搜索房间
     *
     * @param pageListRequest
     * @return
     */
    List<SearchRoomVO> selectBySearch(HomeSearchPageListRequest pageListRequest);

    /**
     * 修改房间用户人数
     *
     * @param roomId
     */
    void updateRoomUserNum(@Param("roomId") Long roomId);

    /**
     * 推荐房间列表
     *
     * @param pageListRequest
     * @return
     */
    List<MyRoomPageListVO> getPopularRoomList(RoomPageListRequest pageListRequest);

    /**
     * 我的房间信息
     *
     * @param accountId
     * @return
     */
    RoomInfo getMyRoomInfo(@Param("accountId") Long accountId);

    /**
     * 房间上锁
     *
     * @param newRoom
     */
    int updateLockRoom(RoomInfo newRoom);

    /**
     * 排行榜
     *
     * @param listRequest
     * @return
     */
    List<RankVO> listRank(HomeRankListRequest listRequest);
}

package com.chitchat.portal.dao.room;

import com.chitchat.common.base.BaseDaoI;
import com.chitchat.portal.bean.room.RoomAccountLink;
import com.chitchat.portal.dto.room.RoomUserPageListRequestDTO;
import com.chitchat.portal.vo.room.RoomAccountLinkVO;
import com.chitchat.portal.vo.room.RoomUserBaseInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by Js on 2022/8/3 .
 **/
public interface RoomAccountLinkDaoI extends BaseDaoI<RoomAccountLink> {

    /**
     * 查询房间在线用户数量
     *
     * @param roomId
     * @return
     */
    int getUserNumByRoomId(@Param("roomId") Long roomId);

    /**
     * 通过房间id和用户id查询关联关系
     *
     * @param roomId
     * @param accountId
     * @return
     */
    RoomAccountLink getByRoomIdAndAccountId(@Param("roomId") Long roomId, @Param("accountId") Long accountId);

    /**
     * 房间在线列表
     *
     * @param pageListRequest
     * @return
     */
    List<RoomAccountLinkVO> listRoomUser(RoomUserPageListRequestDTO pageListRequest);

    /**
     * 获取房间用户基本信息
     *
     * @param roomId
     * @param accountId
     * @return
     */
    RoomUserBaseInfo getRoomUserInfoByRoomIdAndAccountId(@Param("roomId") Long roomId, @Param("accountId") Long accountId);

    /**
     * 获取房间所有在线用户
     *
     * @param roomId
     * @return
     */
    List<RoomAccountLink> listRoomAccountLinkByRoomId(@Param("roomId") Long roomId);
}

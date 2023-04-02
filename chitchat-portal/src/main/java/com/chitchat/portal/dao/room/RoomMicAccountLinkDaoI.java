package com.chitchat.portal.dao.room;

import com.chitchat.common.base.BaseDaoI;
import com.chitchat.common.base.BasePageRequestModel;
import com.chitchat.portal.bean.room.RoomMicAccountLink;
import com.chitchat.portal.vo.room.RoomAccountLinkVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 房间用户麦位关系
 *
 * Created by Js on 2022/9/10 .
 **/
public interface RoomMicAccountLinkDaoI extends BaseDaoI<RoomMicAccountLink> {
    /**
     * 麦上用户列表
     *
     * @param pageListRequest
     * @return
     */
    List<RoomAccountLinkVO> getList(BasePageRequestModel pageListRequest);

    /**
     * 用户id和房间id查询用户上麦信息
     *
     * @param roomId
     * @param accountId
     * @return
     */
    RoomMicAccountLink getByRoomIdAndAccountId(@Param("roomId") Long roomId,
                                               @Param("accountId") Long accountId);
}

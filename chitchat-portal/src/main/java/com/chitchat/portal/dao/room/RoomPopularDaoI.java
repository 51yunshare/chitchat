package com.chitchat.portal.dao.room;

import com.chitchat.common.base.BaseDaoI;
import com.chitchat.portal.bean.room.RoomPopular;
import com.chitchat.portal.vo.room.RoomPopularListVO;

import java.util.List;

public interface RoomPopularDaoI extends BaseDaoI<RoomPopular> {

    /**
     * 获取配置的推荐房间
     *
     * @return
     */
    List<RoomPopularListVO> getAllPopularList();
}

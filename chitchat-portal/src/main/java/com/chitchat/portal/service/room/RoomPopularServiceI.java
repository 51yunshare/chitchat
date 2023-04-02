package com.chitchat.portal.service.room;

import com.chitchat.common.base.BaseServicesI;
import com.chitchat.portal.bean.room.RoomPopular;
import com.chitchat.portal.vo.room.RoomPopularListVO;

import java.util.List;

/**
 * 房间推荐配置
 *
 * Created by Js on 2022/12/2 .
 **/
public interface RoomPopularServiceI extends BaseServicesI<RoomPopular> {

    /**
     * 获取配置的推荐房间
     *
     * @return
     */
    List<RoomPopularListVO> getAllPopularList();
}

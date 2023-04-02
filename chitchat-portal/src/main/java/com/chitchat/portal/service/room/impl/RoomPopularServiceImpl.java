package com.chitchat.portal.service.room.impl;

import com.chitchat.common.base.BaseServicesImpl;
import com.chitchat.portal.bean.room.RoomPopular;
import com.chitchat.portal.dao.room.RoomPopularDaoI;
import com.chitchat.portal.service.room.RoomPopularServiceI;
import com.chitchat.portal.vo.room.RoomPopularListVO;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 首页推荐配置
 *
 * Created by Js on 2022/12/2 .
 **/
@Service
public class RoomPopularServiceImpl extends BaseServicesImpl<RoomPopular, RoomPopularDaoI> implements RoomPopularServiceI {

    /**
     * 获取配置的推荐房间
     *
     * @return
     */
    @Override
    public List<RoomPopularListVO> getAllPopularList() {
        return baseDaoT.getAllPopularList();
    }
}

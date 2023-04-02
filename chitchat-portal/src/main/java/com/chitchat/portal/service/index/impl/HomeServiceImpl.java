package com.chitchat.portal.service.index.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chitchat.common.base.ResultTemplate;
import com.chitchat.common.base.UserDto;
import com.chitchat.common.redis.RedisKey;
import com.chitchat.common.redis.RedisTemplateUtil;
import com.chitchat.common.util.JSONObjectUtil;
import com.chitchat.common.web.jwt.UserUtils;
import com.chitchat.portal.dto.index.HomeRankListRequest;
import com.chitchat.portal.dto.index.HomeSearchPageListRequest;
import com.chitchat.portal.enumerate.EnumHomeSearchType;
import com.chitchat.portal.enumerate.EnumUserFollowType;
import com.chitchat.portal.service.account.AccountInfoServiceI;
import com.chitchat.portal.service.index.HomeServiceI;
import com.chitchat.portal.service.room.RoomServiceI;
import com.chitchat.portal.vo.index.RankVO;
import com.chitchat.portal.vo.index.SearchRoomVO;
import com.chitchat.portal.vo.index.SearchUserVO;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Js on 2022/8/17.
 **/
@Service
public class HomeServiceImpl implements HomeServiceI {

    @Autowired
    private RoomServiceI roomServiceI;
    @Autowired
    private AccountInfoServiceI accountInfoServiceI;
    @Autowired
    private RedisTemplateUtil redisTemplateUtil;


    /**
     * 搜索
     *
     * @param pageListRequest
     * @return
     */
    @Override
    public ResultTemplate doSearch(HomeSearchPageListRequest pageListRequest) {
        final UserDto userDto = UserUtils.getUser();
        pageListRequest.setUserInfo(userDto);
        //查询房间
        if (pageListRequest.getHomeSearchType().equals(EnumHomeSearchType.ROOM)){
            PageInfo<SearchRoomVO> data = roomServiceI.selectBySearch(pageListRequest);
            return new ResultTemplate(data, JSONObjectUtil.parseArray(data.getList()));
        }
        JSONArray result = new JSONArray();
        //查询用户
        PageInfo<SearchUserVO> data = accountInfoServiceI.selectBySearch(pageListRequest);
        //todo 判断用户是否互关，查询redis
        data.getList().forEach(vo ->{
            Boolean isFollow = redisTemplateUtil.sIsMember(RedisKey.getUserFollowing(userDto.getId(), EnumUserFollowType.用户.getIndex()), vo.getId());
            vo.setFollow(isFollow);
            JSONObject ob = JSONObjectUtil.parseObject(vo);
            result.add(ob);
        });
        return new ResultTemplate(data, result);
    }

    /**
     * 排行榜
     *
     * @param listRequest
     * @return
     */
    @Override
    public List<RankVO> listRank(HomeRankListRequest listRequest) {
        return roomServiceI.listRank(listRequest);
    }
}

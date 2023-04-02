package com.chitchat.admin.service.impl;

import com.chitchat.admin.api.enumerate.EnumRankStatType;
import com.chitchat.admin.api.enumerate.EnumRankType;
import com.chitchat.admin.bean.RankInfo;
import com.chitchat.admin.dao.RankInfoDaoI;
import com.chitchat.admin.service.RankInfoServiceI;
import com.chitchat.common.base.BaseServicesImpl;
import org.springframework.stereotype.Service;

/**
 * Created by Js on 2022/11/11 .
 **/
@Service
public class RankInfoServiceImpl extends BaseServicesImpl<RankInfo, RankInfoDaoI> implements RankInfoServiceI {

    /**
     * 统计排行榜
     *
     * 1.房间的贡献值排行榜
     * 2.送礼者排行榜
     * 3.收礼者排行榜
     *
     * @param rankType
     */
    @Override
    public void statRank(EnumRankType rankType) {
        //房间的贡献值排行榜
        baseDaoT.insertStat(EnumRankStatType.ROOM_CONTRIBUTION.getIndex(), rankType.getIndex());
        //送礼者排行榜
        baseDaoT.insertStat(EnumRankStatType.SEND_GIFT.getIndex(), rankType.getIndex());
        //收礼者排行榜
        baseDaoT.insertStat(EnumRankStatType.RECEIVED_GIFTS.getIndex(), rankType.getIndex());
    }
}

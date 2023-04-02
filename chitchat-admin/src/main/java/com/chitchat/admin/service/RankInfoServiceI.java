package com.chitchat.admin.service;

import com.chitchat.admin.api.enumerate.EnumRankType;
import com.chitchat.admin.bean.RankInfo;
import com.chitchat.common.base.BaseServicesI;

/**
 * Created by Js on 2022/11/11 .
 **/
public interface RankInfoServiceI extends BaseServicesI<RankInfo> {

    /**
     * 统计排行榜
     *
     * @param rankType
     */
    void statRank(EnumRankType rankType);
}

package com.chitchat.admin.dao;

import com.chitchat.admin.bean.RankInfo;
import com.chitchat.common.base.BaseDaoI;
import org.apache.ibatis.annotations.Param;

public interface RankInfoDaoI extends BaseDaoI<RankInfo> {

    /**
     * 排行榜统计
     *
     * @param statType-类型
     * @param rankType-日期
     */
    void insertStat(@Param("statType") int statType, @Param("rankType") int rankType);
}

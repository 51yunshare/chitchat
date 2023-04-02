package com.chitchat.admin.job;

import cn.hutool.core.date.DateUtil;
import com.chitchat.admin.api.enumerate.EnumRankType;
import com.chitchat.admin.service.RankInfoServiceI;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 排行榜定时任务
 *
 * Created by Js on 2022/11/10 .
 **/
@Component
@Slf4j
public class RankJob {
    /****** 房间的贡献值排行榜(日/周/月)、送礼者排行榜(日/周/月)、收礼者排行榜(日/周/月) ******/
    @Resource
    private RankInfoServiceI rankInfoServiceI;

    /**
     * 日榜-每天0点
     *
     */
    //0 0 0 */1 * ?
    @XxlJob("dailyJob")
    public void executeDailyRankJob() {
        log.info(">> start daily timer job ...{}", DateUtil.date());
        rankInfoServiceI.statRank(EnumRankType.RANK_DAILY);
    }

    /**
     * 周榜-每周一0点
     *
     * 0 0 0 ? * MON
     *
     */
    @XxlJob("weeklyJob")
    public void executeWeeklyRankJob(){
        log.info(">> start weekly timer job ...{}", DateUtil.date());
        rankInfoServiceI.statRank(EnumRankType.RANK_WEEKLY);
    }

    /**
     * 月榜-每月1号0点
     *
     * 0 0 0 1 * ?
     *
     */
    @XxlJob("monthlyJob")
    public void executeMonthlyRankJob(){
        log.info(">> start monthly timer job ...{}", DateUtil.date());
        rankInfoServiceI.statRank(EnumRankType.RANK_MONTHLY);
    }
}

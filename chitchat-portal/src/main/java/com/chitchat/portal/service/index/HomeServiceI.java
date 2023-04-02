package com.chitchat.portal.service.index;

import com.chitchat.common.base.ResultTemplate;
import com.chitchat.portal.dto.index.HomeRankListRequest;
import com.chitchat.portal.dto.index.HomeSearchPageListRequest;
import com.chitchat.portal.vo.index.RankVO;

import java.util.List;

public interface HomeServiceI {

    /**
     * 搜索
     *
     * @param pageListRequest
     * @return
     */
    ResultTemplate doSearch(HomeSearchPageListRequest pageListRequest);

    /**
     * 排行榜
     *
     * @param listRequest
     * @return
     */
    List<RankVO> listRank(HomeRankListRequest listRequest);
}

package com.chitchat.portal.controller.index;

import com.chitchat.common.base.BaseController;
import com.chitchat.common.base.Result;
import com.chitchat.common.base.ResultTemplate;
import com.chitchat.portal.dto.index.HomeRankListRequest;
import com.chitchat.portal.dto.index.HomeSearchPageListRequest;
import com.chitchat.portal.service.index.HomeServiceI;
import com.chitchat.portal.vo.index.RankVO;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by Js on 2022/8/17.
 **/
@RestController
@Slf4j
@RequestMapping("index")
@Api(value = "HomeController", tags = "首页")
public class HomeController extends BaseController {
    /****** 搜索 *****/

    @Autowired
    private HomeServiceI homeServiceI;

    /**
     * 搜索
     *
     * @param pageListRequest
     * @return
     */
    @ApiOperation("搜索")
    @ApiOperationSupport(ignoreParameters = {"userInfo"})
    @GetMapping(value = "/search")
    public ResultTemplate doSearch(@Validated HomeSearchPageListRequest pageListRequest) {
        return homeServiceI.doSearch(pageListRequest);
    }


    /****** 房间的贡献值排行榜(日/周/月)、送礼者排行榜(日/周/月)、收礼者排行榜(日/周/月) *****/

    /**
     * 排行榜
     *
     * @param listRequest
     * @return
     */
    @ApiOperation("排行榜")
    @GetMapping(value = "/rank")
    public Result<List<RankVO>> listRank(@Validated HomeRankListRequest listRequest) {
        return Result.success(homeServiceI.listRank(listRequest));
    }
}

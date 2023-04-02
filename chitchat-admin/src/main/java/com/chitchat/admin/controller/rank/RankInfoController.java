package com.chitchat.admin.controller.rank;

import com.chitchat.admin.dto.RankInfoInitRequestDTO;
import com.chitchat.admin.service.RankInfoServiceI;
import com.chitchat.common.base.BaseController;
import com.chitchat.common.base.ResultTemplate;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * Created by Js on 2022/11/11 .
 **/
@RestController
@RequestMapping("rank")
@Api(value = "RankInfoController", tags = "排行榜")
public class RankInfoController extends BaseController {
    /****** 房间的贡献值排行榜(日/周/月)、送礼者排行榜(日/周/月)、收礼者排行榜(日/周/月) ******/
    @Resource
    private RankInfoServiceI rankInfoServiceI;

    /**
     * 排行榜
     *
     * @param initRequestDTO
     * @return
     */
    @ApiOperation("排行榜-初始化")
    @PostMapping(value = "/init")
    public ResultTemplate init(@Validated RankInfoInitRequestDTO initRequestDTO) {
        rankInfoServiceI.statRank(initRequestDTO.getEnumRankType());
        return success();
    }
}

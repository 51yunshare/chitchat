package com.chitchat.admin.controller.base;

import com.chitchat.admin.api.vo.ActivityInfoFeignVO;
import com.chitchat.admin.dto.ActivityInfoAddRequestDTO;
import com.chitchat.admin.dto.ActivityInfoPageListRequestDTO;
import com.chitchat.admin.service.ActivityInfoServiceI;
import com.chitchat.common.base.BaseController;
import com.chitchat.common.base.ResultTemplate;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 活动管理
 *
 * Created by Js on 2022/9/15 .
 **/
@RestController
@RequestMapping("base/act")
@Api(value = "ActivityInfoController", tags = "活动信息管理")
@Slf4j
@RequiredArgsConstructor
public class ActivityInfoController extends BaseController {

    private final ActivityInfoServiceI activityInfoServiceI;

    /**
     * 活动列表
     *
     * @param pageListRequest
     * @return
     */
    @ApiOperation("活动列表")
    @GetMapping(value = "/list")
    @ApiOperationSupport(ignoreParameters = {"userInfo"})
    public ResultTemplate list(ActivityInfoPageListRequestDTO pageListRequest) {
        return activityInfoServiceI.getList(pageListRequest);
    }
    /**
     * 新增活动
     *
     * @param dto
     * @return
     */
    @ApiOperation("活动新增")
    @PostMapping("/add")
    public ResultTemplate doAdd(@Validated ActivityInfoAddRequestDTO dto) {
        activityInfoServiceI.doAdd(dto);
        return this.success();
    }

    /**
     * 获取活动列表通过类型
     *
     * @param type
     * @return
     */
    @ApiOperation(value = "获取活动列表通过类型", hidden = true)
    @GetMapping(value = "/getListByType")
    public ResultTemplate getListByType(@RequestParam(value = "type", required = false) Integer type) {
        List<ActivityInfoFeignVO> list = activityInfoServiceI.getListByType(type);
        return this.success(list);
    }
}

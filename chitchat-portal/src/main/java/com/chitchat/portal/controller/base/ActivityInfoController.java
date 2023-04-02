package com.chitchat.portal.controller.base;

import com.chitchat.common.base.BaseController;
import com.chitchat.common.base.ResultTemplate;
import com.chitchat.portal.service.base.ActivityInfoServiceI;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 活动
 *
 * Created by Js on 2022/9/14.
 **/

@RestController
@RequestMapping("base/act")
@Api(value = "ActivityController", tags = "活动信息")
@Slf4j
@RequiredArgsConstructor
public class ActivityInfoController extends BaseController {

    private final ActivityInfoServiceI activityInfoServiceI;

    /**
     * Banner 列表
     *
     * @return
     */
    @ApiOperation("首页Banner列表")
    @GetMapping(value = "/list/{type}")
    public ResultTemplate list(@ApiParam("活动类型：1-首页Banner 2-其他") @PathVariable Integer type) {
        return activityInfoServiceI.getList(type);
    }
}

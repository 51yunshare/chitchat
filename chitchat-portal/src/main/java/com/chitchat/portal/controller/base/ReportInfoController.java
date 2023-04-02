package com.chitchat.portal.controller.base;

import com.chitchat.common.base.BaseController;
import com.chitchat.common.base.ResultTemplate;
import com.chitchat.portal.dto.base.ReportInfoAddRequestDTO;
import com.chitchat.portal.service.base.ReportInfoServiceI;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户举报(房间/用户)
 *
 * Created by Js on 2022/8/26 .
 **/
@RestController
@RequestMapping("user/report")
@Api(value = "UserReportController", tags = "用户举报管理")
@Slf4j
public class ReportInfoController extends BaseController {
    /** 用户举报新增 **/

    @Autowired
    private ReportInfoServiceI reportInfoServiceI;

    /**
     * 用户举报
     *
     * @param dto
     * @return
     */
    @ApiOperation(value = "用户举报")
    @PostMapping("/add")
    public ResultTemplate doAddReport(@RequestBody @Validated ReportInfoAddRequestDTO dto) {
        reportInfoServiceI.doAddReport(dto);
        return this.success();
    }

}

package com.chitchat.portal.controller.base;

import com.chitchat.common.base.BaseController;
import com.chitchat.common.base.ResultTemplate;
import com.chitchat.portal.dto.base.FeedbackAddRequestDTO;
import com.chitchat.portal.dto.base.FeedbackPageListRequestDTO;
import com.chitchat.portal.service.base.FeedbackInfoServiceI;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 用户反馈
 *
 * Created by Js on 2022/8/26 .
 **/
@RestController
@RequestMapping("user/feedback")
@Api(value = "UserFeedbackController", tags = "用户反馈管理")
@Slf4j
public class FeedbackController extends BaseController {
    /** 反馈新增、用户反馈列表 **/

    @Autowired
    private FeedbackInfoServiceI feedbackInfoServiceI;

    /**
     * 反馈列表
     *
     * @param pageListRequest
     * @return
     */
    @ApiOperation("反馈列表")
    @ApiOperationSupport(ignoreParameters = {"userInfo"})
    @GetMapping(value = "/list")
    public ResultTemplate list(FeedbackPageListRequestDTO pageListRequest) {
        return feedbackInfoServiceI.getList(pageListRequest);
    }

    /**
     * 用户反馈
     *
     * @param dto
     * @return
     */
    @ApiOperation(value = "用户反馈")
    @PostMapping("/add")
    public ResultTemplate doAddFeedback(@RequestBody @Validated FeedbackAddRequestDTO dto) {
        feedbackInfoServiceI.doAddFeedback(dto);
        return this.success();
    }

}

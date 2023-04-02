package com.chitchat.portal.controller.account;

import com.chitchat.common.base.BaseController;
import com.chitchat.common.base.ResultTemplate;
import com.chitchat.portal.dto.account.AccountVisitorsInfoAddDTO;
import com.chitchat.portal.dto.account.VisitorsInfoPageListRequestDTO;
import com.chitchat.portal.service.account.AccountVisitorsInfoServiceI;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;

/**
 * 用户访客信息
 *
 * Created by Js on 2022/9/26.
 **/
@RestController
@RequestMapping("personal/visitors")
@Api(value = "AccountVisitorsInfoController", tags = "用户访客信息")
@ApiIgnore
public class AccountVisitorsInfoController extends BaseController {
    /********* 访客信息列表、访客信息新增 *********/
    @Resource
    private AccountVisitorsInfoServiceI accountVisitorsInfoServiceI;

    /**
     * 访客信息
     *
     * @param pageListRequest
     * @return
     */
    @ApiOperation("访客信息列表")
    @ApiOperationSupport(ignoreParameters = {"userInfo"})
    @GetMapping(value = "/list")
    public ResultTemplate list(VisitorsInfoPageListRequestDTO pageListRequest) {
        return accountVisitorsInfoServiceI.getList(pageListRequest);
    }

    /**
     * 访客信息新增
     *
     * @param dto
     * @return
     */
    @ApiOperation(value = "访客信息新增")
    @PostMapping(value = "/add")
    public ResultTemplate doAdd(@RequestBody @Validated AccountVisitorsInfoAddDTO dto) {
        accountVisitorsInfoServiceI.doAdd(dto);
        return this.success();
    }

}

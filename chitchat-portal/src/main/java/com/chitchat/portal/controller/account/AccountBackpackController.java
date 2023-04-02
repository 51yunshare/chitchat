package com.chitchat.portal.controller.account;

import com.chitchat.common.base.BaseController;
import com.chitchat.common.base.ResultTemplate;
import com.chitchat.portal.api.dto.AccountBackpackInfoAddDTO;
import com.chitchat.portal.dto.account.BackpackPageListRequestDTO;
import com.chitchat.portal.service.account.AccountBackpackServiceI;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 用户背包
 * Created by Js on 2022/9/26.
 **/
@RestController
@RequestMapping("personal/backpack")
@Api(value = "AccountBackpackController", tags = "背包管理")
public class AccountBackpackController extends BaseController {
    /********* 背包列表(装备中、可用的)、已过期列表、激活、下架 *********/
    @Resource
    private AccountBackpackServiceI accountBackpackServiceI;

    /**
     * 背包信息
     *
     * @param pageListRequest
     * @return
     */
    @ApiOperation("背包列表")
    @ApiOperationSupport(ignoreParameters = {"userInfo"})
    @GetMapping(value = "/list")
    public ResultTemplate list(BackpackPageListRequestDTO pageListRequest) {
        return accountBackpackServiceI.getList(pageListRequest);
    }

    /**
     * 背包新增记录
     *
     * @param dto
     * @return
     */
    @ApiOperation(value = "背包新增道具", hidden = true)
    @PostMapping(value = "/add")
    public ResultTemplate doAdd(@RequestBody AccountBackpackInfoAddDTO dto) {
        accountBackpackServiceI.doAdd(dto);
        return this.success();
    }

}

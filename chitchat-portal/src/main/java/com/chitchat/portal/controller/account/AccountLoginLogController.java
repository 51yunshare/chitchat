package com.chitchat.portal.controller.account;

import com.chitchat.common.base.BaseController;
import com.chitchat.common.base.ResultTemplate;
import com.chitchat.portal.api.dto.AccountLoginLogAddDTO;
import com.chitchat.portal.service.account.AccountLoginLogServiceI;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 成员
 *
 * Created by Js on 2022/7/27 .
 **/
@RestController
@RequestMapping("login/log")
@Slf4j
@Api(value = "AccountLoginLogController", tags = "会员登录日志管理")
public class AccountLoginLogController extends BaseController {

    @Autowired
    private AccountLoginLogServiceI accountLoginLogServiceI;

    /**
     * 登录日志
     *
     * @param logAddDTO
     * @return
     */
    @ApiOperation(value = "会员登录日志添加", hidden = true)
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResultTemplate doAdd(@RequestBody AccountLoginLogAddDTO logAddDTO) {
        accountLoginLogServiceI.doAdd(logAddDTO);
        return this.success();
    }

}

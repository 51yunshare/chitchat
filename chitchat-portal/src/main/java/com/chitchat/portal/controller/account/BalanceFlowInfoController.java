package com.chitchat.portal.controller.account;

import com.chitchat.common.base.BaseController;
import com.chitchat.common.base.ResultTemplate;
import com.chitchat.portal.dto.account.BalanceFlowPageListRequestDTO;
import com.chitchat.portal.service.account.BalanceFlowInfoServiceI;
import com.chitchat.portal.vo.account.BalanceFlowInfoListVO;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 余额流水记录管理
 *
 * Created by Js on 2022/9/16 .
 **/
@RestController
@RequestMapping("user/balanceFlow")
@Api(value = "BalanceFlowInfoController", tags = "用户余额流水记录管理")
public class BalanceFlowInfoController extends BaseController {

    @Resource
    private BalanceFlowInfoServiceI balanceFlowInfoServiceI;

    /**
     * 余额流水
     *
     * @return
     */
    @ApiOperation(value = "用户流水记录",response = BalanceFlowInfoListVO.class)
    @ApiOperationSupport(ignoreParameters = {"userInfo"})
    @GetMapping(value = "/list")
    public ResultTemplate list(BalanceFlowPageListRequestDTO dto) {
        return balanceFlowInfoServiceI.getList(dto);
    }
}

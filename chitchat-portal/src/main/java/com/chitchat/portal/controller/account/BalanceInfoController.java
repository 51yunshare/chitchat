package com.chitchat.portal.controller.account;

import com.chitchat.common.base.BaseController;
import com.chitchat.common.base.ResultTemplate;
import com.chitchat.portal.dto.account.BalancePageListRequestDTO;
import com.chitchat.portal.service.account.BalanceInfoServiceI;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.math.BigDecimal;

/**
 * 余额管理
 *
 * Created by Js on 2022/9/16 .
 **/
@RestController
@RequestMapping("user/balance")
@Api(value = "BalanceInfoController", tags = "用户余额管理")
public class BalanceInfoController extends BaseController {

    @Resource
    private BalanceInfoServiceI balanceInfoServiceI;

    /**
     * 余额查询-type余额类型
     *
     * @return
     */
    @ApiOperation("用户余额")
    @ApiOperationSupport(ignoreParameters = {"userInfo"})
    @GetMapping(value = "/list")
    public ResultTemplate list(BalancePageListRequestDTO dto) {
        return balanceInfoServiceI.getList(dto);
    }

    /**
     * 余额判断
     *
     * @return
     */
    @ApiOperation(value = "用户余额判断",hidden = true)
    @GetMapping(value = "/check")
    public boolean checkBalance(@RequestParam Long accountId, @RequestParam BigDecimal goodsPrice) {
        return balanceInfoServiceI.checkBalance(accountId, goodsPrice);
    }

    /**
     * 扣减余额并生成流水
     *
     * @param accountId
     * @param goodsPrice
     * @return
     */
    @ApiOperation(value = "扣减余额并生成流水", hidden = true)
    @PostMapping(value = "/accountBalanceSub")
    public boolean accountBalanceSub(@RequestParam Long accountId, @RequestParam BigDecimal goodsPrice) {
        return balanceInfoServiceI.accountBalanceAndSub(accountId, goodsPrice);
    }
}

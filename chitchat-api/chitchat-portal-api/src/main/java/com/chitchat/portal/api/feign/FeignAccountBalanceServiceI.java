package com.chitchat.portal.api.feign;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;

/**
 * 用户余额信息
 *
 * Created by Js on 2022/7/30.
 */
@FeignClient("chitchat-portal")
public interface FeignAccountBalanceServiceI {

    /**
     * 判断用户余额
     *
     * @param accountId
     * @param goodsPrice
     */
    @GetMapping("/user/balance/check")
    boolean checkAccountBalance(@RequestParam("accountId") Long accountId, @RequestParam("goodsPrice") BigDecimal goodsPrice);

    /**
     * 扣减余额并生成流水
     *
     * @param accountId
     * @param goodsPrice
     */
    @PostMapping("/user/balance/accountBalanceSub")
    boolean accountBalanceSub(@RequestParam("accountId") Long accountId, @RequestParam("goodsPrice") BigDecimal goodsPrice);
}

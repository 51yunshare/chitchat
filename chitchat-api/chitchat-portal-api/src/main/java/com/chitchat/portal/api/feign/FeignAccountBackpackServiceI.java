package com.chitchat.portal.api.feign;


import com.chitchat.common.base.ResultTemplate;
import com.chitchat.portal.api.dto.AccountBackpackInfoAddDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * 用户背包
 *
 * Created by Js on 2022/7/30.
 */
@FeignClient("chitchat-portal")
public interface FeignAccountBackpackServiceI {

    /**
     * 用户背包添加
     *
     * @param dto
     */
    @PostMapping("/personal/backpack/add")
    ResultTemplate doAdd(@RequestBody AccountBackpackInfoAddDTO dto);
}

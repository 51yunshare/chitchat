package com.chitchat.portal.api.feign;


import com.chitchat.common.base.ResultTemplate;
import com.chitchat.portal.api.dto.AccountLoginLogAddDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * 添加用户登录日志
 *
 * Created by Js on 2022/7/30.
 */
@FeignClient("chitchat-portal")
public interface FeignPortalAccountLoginLogServiceI {
    /**
     * 添加登录日志
     *
     * @param dto
     * @return
     */
    @PostMapping("/login/log/doAdd")
    ResultTemplate doAdd(@RequestBody AccountLoginLogAddDTO dto);

}

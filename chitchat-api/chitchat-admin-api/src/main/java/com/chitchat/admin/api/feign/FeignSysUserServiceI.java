package com.chitchat.admin.api.feign;

import com.chitchat.common.base.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 后台用户
 *
 * Created by Js on 2022/7/30.
 */
@FeignClient("chitchat-admin")
public interface FeignSysUserServiceI {

    @GetMapping("/admin/loadByUsername")
    UserDto loadUserByUsername(@RequestParam String username);
}

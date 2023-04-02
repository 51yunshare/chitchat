package com.chitchat.portal.api.feign;


import com.chitchat.common.base.ResultTemplate;
import com.chitchat.common.base.UserDto;
import com.chitchat.portal.api.dto.MemberDTO;
import com.chitchat.portal.api.vo.AccountInfoVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * 门户用户
 *
 * Created by Js on 2022/7/30.
 */
@FeignClient("chitchat-portal")
public interface FeignPortalAccountServiceI {
    /**
     * 根据用户名获取通用用户信息
     *
     * @param username
     * @return
     */
    @GetMapping("/sso/loadByUsername")
    UserDto loadUserByUsername(@RequestParam String username);

    /**
     * 第三方认证
     *
     * @param openId
     * @param identityType
     * @return
     */
    @GetMapping("/sso/openid")
    UserDto loadUserByOpenId(@RequestParam("openId") String openId, @RequestParam("identityType") Integer identityType);

    @PostMapping("/sso/addMember")
    ResultTemplate addMember(@RequestBody MemberDTO memberDTO);

    /**
     * 手机号密码认证
     *
     * @param mobile
     * @return
     */
    @GetMapping("/sso/loadUserByMobile")
    UserDto loadUserByMobile(@RequestParam String mobile);

    /**
     * 获取用户信息
     *
     * @param id
     * @return
     */
    @GetMapping("/sso/getById/{id}")
    AccountInfoVO getById(@PathVariable Long id);

}

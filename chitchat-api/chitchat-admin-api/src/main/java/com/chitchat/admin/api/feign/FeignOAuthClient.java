package com.chitchat.admin.api.feign;

import com.chitchat.admin.api.dto.ClientAuthDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 客户端
 *
 * Created by Js on 2022/8/10.
 */
@FeignClient("chitchat-admin")
public interface FeignOAuthClient {

    @GetMapping("/admin/oauth/getOAuth2ClientById")
    ClientAuthDTO getOAuth2ClientById(@RequestParam String clientId);
}

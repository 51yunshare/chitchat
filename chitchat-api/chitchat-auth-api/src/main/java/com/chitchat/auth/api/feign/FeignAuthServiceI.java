package com.chitchat.auth.api.feign;

import com.chitchat.common.base.ResultTemplate;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/**
 * Created by Js on 2022/8/7.
 **/
@FeignClient("chitchat-auth")
public interface FeignAuthServiceI {


    @PostMapping(value = "/oauth/token")
    ResultTemplate getAccessToken(@RequestParam Map<String, String> parameters);
}

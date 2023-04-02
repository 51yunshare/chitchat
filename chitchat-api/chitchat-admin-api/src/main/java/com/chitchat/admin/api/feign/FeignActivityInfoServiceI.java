package com.chitchat.admin.api.feign;


import com.chitchat.common.base.ResultTemplate;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 活动远程调用
 *
 */
@FeignClient("chitchat-admin")
public interface FeignActivityInfoServiceI {

    /**
     * 获取活动列表通过类型
     *
     * @param type
     * @return
     */
    @GetMapping("/base/act/getListByType")
    ResultTemplate getListByType(@RequestParam(required = false) Integer type);
}

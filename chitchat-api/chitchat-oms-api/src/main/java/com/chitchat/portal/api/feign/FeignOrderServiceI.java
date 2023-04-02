package com.chitchat.portal.api.feign;

import org.springframework.cloud.openfeign.FeignClient;

/**
 * 订单相关
 *
 * Created by Js on 2022/9/30 .
 **/
@FeignClient("chitchat-oms")
public interface FeignOrderServiceI {
}

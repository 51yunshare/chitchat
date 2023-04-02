package com.chitchat.admin.api.feign;


import com.chitchat.admin.api.vo.DictInfoVO;
import com.chitchat.common.base.ResultTemplate;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 字典远程调用
 *
 */
@FeignClient("chitchat-admin")
public interface FeignDictInfoServiceI {


    @GetMapping("/base/dict/getDictByType/{type}")
    ResultTemplate getDictByType(@PathVariable(value = "type") Integer type);

    @GetMapping("/base/dict/getDictById")
    DictInfoVO getDictById(@RequestParam Long dictId);
}

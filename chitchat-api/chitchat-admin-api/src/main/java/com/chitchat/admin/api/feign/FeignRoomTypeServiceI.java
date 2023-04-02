package com.chitchat.admin.api.feign;


import com.alibaba.fastjson.JSONArray;
import com.chitchat.admin.api.vo.RoomTypeInfoVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 房间类型远程调用
 *
 */
@FeignClient("chitchat-admin")
public interface FeignRoomTypeServiceI {

    /**
     * 房间类型获取
     *
     * @param id
     * @return
     */
    @GetMapping("/base/roomType/getById")
    RoomTypeInfoVO getById(@RequestParam Integer id);

    /**
     * 获取房间类型
     *
     * @return
     */
    @GetMapping("/base/roomType/getComList")
    JSONArray getComList();
}

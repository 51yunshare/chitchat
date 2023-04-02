package com.chitchat.admin.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chitchat.admin.bean.RoomType;
import com.chitchat.admin.dto.RoomTypeAddRequestDTO;
import com.chitchat.admin.dto.RoomTypePageListRequestDTO;
import com.chitchat.common.base.BaseServicesI;
import com.chitchat.common.base.ResultTemplate;

/**
 * 房间类型 service接口
 */
public interface RoomTypeServiceI extends BaseServicesI<RoomType> {

    /**
     * 房间类型列表
     *
     * @param pageListRequest
     * @return
     */
    ResultTemplate getList(RoomTypePageListRequestDTO pageListRequest);


    /**
     * 新增房间类型
     *
     * @param dto
     */
    void doAdd(RoomTypeAddRequestDTO dto);

    /**
     * 获取房间类型下拉框
     *
     * @return
     */
    JSONArray getRoomTypeComList();
}

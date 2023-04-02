package com.chitchat.admin.service;

import com.chitchat.admin.bean.GiftInfo;
import com.chitchat.admin.dto.GiftAddRequestDTO;
import com.chitchat.admin.dto.GiftPageListRequestDTO;
import com.chitchat.common.base.BaseServicesI;
import com.chitchat.common.base.ResultTemplate;

/**
 * 礼物service接口
 */
public interface GiftInfoServiceI extends BaseServicesI<GiftInfo> {

    /**
     * 礼物列表
     *
     * @param pageListRequest
     * @return
     */
    ResultTemplate getList(GiftPageListRequestDTO pageListRequest);

    /**
     * 礼物新增
     *
     * @param dto
     */
    void doAdd(GiftAddRequestDTO dto);
}

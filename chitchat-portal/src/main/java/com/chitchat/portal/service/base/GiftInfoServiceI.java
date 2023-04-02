package com.chitchat.portal.service.base;

import com.alibaba.fastjson.JSONObject;
import com.chitchat.common.base.BaseServicesI;
import com.chitchat.common.base.ResultTemplate;
import com.chitchat.portal.bean.base.GiftInfo;
import com.chitchat.portal.dto.base.GiftPageListRequestDTO;

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
     * 礼物类型
     *
     * @return
     */
    ResultTemplate getGiftType();

    /**
     * 获取礼物版本
     *
     * @return
     */
    JSONObject getGiftVersion();

    /**
     * 礼物下载
     *
     */
    void downGift();
}

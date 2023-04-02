package com.chitchat.portal.service.base;

import com.chitchat.common.base.ResultTemplate;

/**
 * 活动service接口
 */
public interface ActivityInfoServiceI {

    /**
     * 首页Banner列表
     *
     * @return
     * @param type
     */
    ResultTemplate getList(Integer type);
}

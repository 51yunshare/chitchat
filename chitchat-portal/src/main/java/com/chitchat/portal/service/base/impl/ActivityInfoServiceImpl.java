package com.chitchat.portal.service.base.impl;

import com.chitchat.admin.api.feign.FeignActivityInfoServiceI;
import com.chitchat.common.base.ResultTemplate;
import com.chitchat.portal.service.base.ActivityInfoServiceI;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 活动service实现类
 */
@Service
@RequiredArgsConstructor
public class ActivityInfoServiceImpl implements ActivityInfoServiceI {

    private final FeignActivityInfoServiceI feignActivityInfoServiceI;

    /**
     * 首页Banner列表
     *
     * @return
     * @param type
     */
    @Override
    public ResultTemplate getList(Integer type) {
        return feignActivityInfoServiceI.getListByType(type);
    }
}

package com.chitchat.admin.service;

import com.chitchat.admin.api.vo.ActivityInfoFeignVO;
import com.chitchat.admin.bean.ActivityInfo;
import com.chitchat.admin.dto.ActivityInfoAddRequestDTO;
import com.chitchat.admin.dto.ActivityInfoPageListRequestDTO;
import com.chitchat.common.base.BaseServicesI;
import com.chitchat.common.base.ResultTemplate;

import java.util.List;

/**
 * 活动service接口
 */
public interface ActivityInfoServiceI extends BaseServicesI<ActivityInfo> {

    /**
     * 礼物列表
     *
     * @param pageListRequest
     * @return
     */
    ResultTemplate getList(ActivityInfoPageListRequestDTO pageListRequest);

    /**
     * 礼物新增
     *
     * @param dto
     */
    void doAdd(ActivityInfoAddRequestDTO dto);

    /**
     * 获取活动列表通过类型
     *
     * @param type
     * @return
     */
    List<ActivityInfoFeignVO> getListByType(Integer type);
}

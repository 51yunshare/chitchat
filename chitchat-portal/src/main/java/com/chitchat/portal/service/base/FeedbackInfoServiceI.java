package com.chitchat.portal.service.base;

import com.chitchat.common.base.BaseServicesI;
import com.chitchat.common.base.ResultTemplate;
import com.chitchat.portal.bean.base.FeedbackInfo;
import com.chitchat.portal.dto.base.FeedbackAddRequestDTO;
import com.chitchat.portal.dto.base.FeedbackPageListRequestDTO;

/**
 * 反馈
 */
public interface FeedbackInfoServiceI extends BaseServicesI<FeedbackInfo> {

    /**
     * 反馈列表
     *
     * @param pageListRequest
     * @return
     */
    ResultTemplate getList(FeedbackPageListRequestDTO pageListRequest);

    /**
     * 用户反馈
     *
     * @param dto
     */
    void doAddFeedback(FeedbackAddRequestDTO dto);
}

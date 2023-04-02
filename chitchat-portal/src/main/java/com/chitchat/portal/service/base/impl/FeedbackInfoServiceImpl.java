package com.chitchat.portal.service.base.impl;

import com.chitchat.common.base.BaseServicesImpl;
import com.chitchat.common.base.ResultTemplate;
import com.chitchat.common.base.UserDto;
import com.chitchat.common.util.BeanUtils;
import com.chitchat.common.util.JSONObjectUtil;
import com.chitchat.common.web.jwt.UserUtils;
import com.chitchat.portal.bean.base.FeedbackInfo;
import com.chitchat.portal.dao.base.FeedbackInfoDaoI;
import com.chitchat.portal.dto.base.FeedbackAddRequestDTO;
import com.chitchat.portal.dto.base.FeedbackPageListRequestDTO;
import com.chitchat.portal.service.base.FeedbackInfoServiceI;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 反馈service实现类
 */
@Service
public class FeedbackInfoServiceImpl extends BaseServicesImpl<FeedbackInfo, FeedbackInfoDaoI> implements FeedbackInfoServiceI {

    /**
     * 反馈列表
     *
     * @param pageListRequest
     * @return
     */
    @Override
    public ResultTemplate getList(FeedbackPageListRequestDTO pageListRequest) {
        PageInfo<FeedbackInfo> data = list(pageListRequest);
        return new ResultTemplate(data, JSONObjectUtil.parseArray(data.getList()));
    }

    /**
     * 用户反馈
     *
     * @param dto
     */
    @Override
    @Transactional
    public void doAddFeedback(FeedbackAddRequestDTO dto) {
        UserDto userDto = UserUtils.getUser();
        FeedbackInfo feedbackInfo = BeanUtils.copyProperties(dto, FeedbackInfo.class);
        feedbackInfo.setAccountId(userDto.getId());
        feedbackInfo.setNickName(userDto.getNickName());
        baseDaoT.insert(feedbackInfo);
    }
}

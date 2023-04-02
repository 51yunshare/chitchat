package com.chitchat.portal.service.account.impl;

import com.chitchat.common.base.BaseServicesImpl;
import com.chitchat.common.base.ResultTemplate;
import com.chitchat.common.util.BeanUtils;
import com.chitchat.common.util.JSONObjectUtil;
import com.chitchat.common.web.jwt.UserUtils;
import com.chitchat.portal.bean.account.BalanceFlowInfo;
import com.chitchat.portal.dao.account.BalanceFlowInfoDaoI;
import com.chitchat.portal.dto.account.BalanceFlowPageListRequestDTO;
import com.chitchat.portal.service.account.BalanceFlowInfoServiceI;
import com.chitchat.portal.vo.account.BalanceFlowInfoListVO;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

/**
 * 流水记录
 *
 * Created by Js on 2022/9/16 .
 **/
@Service
public class BalanceFlowInfoServiceImpl extends BaseServicesImpl<BalanceFlowInfo, BalanceFlowInfoDaoI> implements BalanceFlowInfoServiceI {

    /**
     * 流水列表
     *
     * @param dto
     * @return
     */
    @Override
    public ResultTemplate getList(BalanceFlowPageListRequestDTO dto) {
        dto.setUserInfo(UserUtils.getUser());
        PageHelper.startPage(dto.getCp(), dto.getRows());
        PageInfo<BalanceFlowInfo> data = new PageInfo(baseDaoT.list(dto));
        return new ResultTemplate(data, JSONObjectUtil.parseArray(BeanUtils.convertList(data.getList(), BalanceFlowInfoListVO.class)));
    }
}

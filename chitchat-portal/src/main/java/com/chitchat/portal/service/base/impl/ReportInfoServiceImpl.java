package com.chitchat.portal.service.base.impl;

import com.chitchat.common.base.BaseServicesImpl;
import com.chitchat.common.base.ResultTemplate;
import com.chitchat.common.base.UserDto;
import com.chitchat.common.util.BeanUtils;
import com.chitchat.common.web.jwt.UserUtils;
import com.chitchat.portal.bean.account.AccountInfo;
import com.chitchat.portal.bean.base.ReportInfo;
import com.chitchat.portal.bean.room.RoomInfo;
import com.chitchat.portal.dao.base.ReportInfoDaoI;
import com.chitchat.portal.dto.base.ReportInfoAddRequestDTO;
import com.chitchat.portal.dto.room.RoomForbiddenPageListRequest;
import com.chitchat.portal.service.account.AccountInfoServiceI;
import com.chitchat.portal.service.base.ReportInfoServiceI;
import com.chitchat.portal.service.room.RoomServiceI;
import com.chitchat.portal.vo.room.RoomForbiddenVO;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 举报信息 Service实现
 *
 * Created by Js on 2022/8/26 .
 */
@Service
public class ReportInfoServiceImpl extends BaseServicesImpl<ReportInfo, ReportInfoDaoI> implements ReportInfoServiceI {

    @Autowired
    private RoomServiceI roomServiceI;
    @Autowired
    private AccountInfoServiceI accountInfoServiceI;

    /**
     * 获取禁止用户列表
     *
     * @param pageListRequest
     * @return
     */
    @Override
    public ResultTemplate getList(RoomForbiddenPageListRequest pageListRequest) {
        PageHelper.startPage(pageListRequest.getCp(), pageListRequest.getRows());
        PageInfo<RoomForbiddenVO> data = new PageInfo(baseDaoT.list(pageListRequest));
        return new ResultTemplate(data, data.getList());
    }

    /**
     * 用户举报
     *
     * @param dto
     */
    @Override
    @Transactional
    public void doAddReport(ReportInfoAddRequestDTO dto) {
        UserDto userDto = UserUtils.getUser();
        switch (dto.getEnumReportType()){
            case 房间:
                RoomInfo roomInfo = roomServiceI.getById(dto.getTargetId(), "房间信息");
                dto.setTargetName(roomInfo.getRoomName());
            default:
                AccountInfo accountInfo = accountInfoServiceI.getById(dto.getTargetId(), "用户信息");
                dto.setTargetName(accountInfo.getNickName());
        }
        ReportInfo reportInfo = BeanUtils.copyProperties(dto, ReportInfo.class);
        reportInfo.setAccountId(userDto.getId());
        reportInfo.setNickName(userDto.getNickName());
        baseDaoT.insert(reportInfo);
    }
}

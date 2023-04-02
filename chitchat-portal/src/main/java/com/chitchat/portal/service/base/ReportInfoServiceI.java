package com.chitchat.portal.service.base;

import com.chitchat.common.base.BaseServicesI;
import com.chitchat.common.base.ResultTemplate;
import com.chitchat.portal.bean.base.ReportInfo;
import com.chitchat.portal.dto.base.ReportInfoAddRequestDTO;
import com.chitchat.portal.dto.room.RoomForbiddenPageListRequest;

/**
* 举报信息 Service
 *
 * Created by Js on 2022/8/26 .
*/
public interface ReportInfoServiceI extends BaseServicesI<ReportInfo> {

    /**
     * 获取禁止用户列表
     *
     * @param pageListRequest
     * @return
     */
    ResultTemplate getList(RoomForbiddenPageListRequest pageListRequest);

    /**
     * 用户举报
     *
     * @param dto
     */
    void doAddReport(ReportInfoAddRequestDTO dto);
}

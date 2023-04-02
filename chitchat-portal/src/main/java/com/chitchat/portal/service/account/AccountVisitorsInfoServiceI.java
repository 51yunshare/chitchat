package com.chitchat.portal.service.account;

import com.chitchat.common.base.BaseServicesI;
import com.chitchat.common.base.ResultTemplate;
import com.chitchat.portal.bean.account.AccountVisitorsInfo;
import com.chitchat.portal.dto.account.AccountVisitorsInfoAddDTO;
import com.chitchat.portal.dto.account.VisitorsInfoPageListRequestDTO;

/**
 * 用户主页访客service接口
 */
public interface AccountVisitorsInfoServiceI extends BaseServicesI<AccountVisitorsInfo> {

    /**
     * 访客信息列表
     *
     * @param pageListRequest
     * @return
     */
    ResultTemplate getList(VisitorsInfoPageListRequestDTO pageListRequest);

    /**
     * 访客信息新增
     *
     * @param dto
     */
    void doAdd(AccountVisitorsInfoAddDTO dto);
}

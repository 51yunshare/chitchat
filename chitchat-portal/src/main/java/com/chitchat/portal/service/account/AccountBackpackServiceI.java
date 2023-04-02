package com.chitchat.portal.service.account;

import com.chitchat.common.base.BaseServicesI;
import com.chitchat.common.base.ResultTemplate;
import com.chitchat.portal.api.dto.AccountBackpackInfoAddDTO;
import com.chitchat.portal.bean.account.AccountBackpackInfo;
import com.chitchat.portal.dto.account.BackpackPageListRequestDTO;

/**
 * 用户背包service接口
 */
public interface AccountBackpackServiceI extends BaseServicesI<AccountBackpackInfo> {

    /**
     * 背包列表
     *
     * @param pageListRequest
     * @return
     */
    ResultTemplate getList(BackpackPageListRequestDTO pageListRequest);

    /**
     * 背包新增记录
     *
     * @param dto
     */
    void doAdd(AccountBackpackInfoAddDTO dto);
}

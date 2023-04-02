package com.chitchat.portal.service.account;

import com.chitchat.common.base.BaseServicesI;
import com.chitchat.portal.api.dto.AccountLoginLogAddDTO;
import com.chitchat.portal.bean.account.AccountLoginLog;

/**
 * 登录日志
 *
 */
public interface AccountLoginLogServiceI extends BaseServicesI<AccountLoginLog> {
    /**
     * 登录日志
     *
     * @param logAddDTO
     * @return
     */
    void doAdd(AccountLoginLogAddDTO logAddDTO);
}

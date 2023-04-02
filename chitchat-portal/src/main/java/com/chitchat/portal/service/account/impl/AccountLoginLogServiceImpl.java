package com.chitchat.portal.service.account.impl;

import com.chitchat.common.base.BaseServicesImpl;
import com.chitchat.common.util.BeanUtils;
import com.chitchat.common.util.ServletUtils;
import com.chitchat.portal.api.dto.AccountLoginLogAddDTO;
import com.chitchat.portal.bean.account.AccountLoginLog;
import com.chitchat.portal.dao.account.AccountLoginLogDaoI;
import com.chitchat.portal.service.account.AccountLoginLogServiceI;
import org.springframework.stereotype.Service;

/**
 * 登录日志实现类
 */
@Service
public class AccountLoginLogServiceImpl extends BaseServicesImpl<AccountLoginLog, AccountLoginLogDaoI> implements AccountLoginLogServiceI {

    @Override
    public void doAdd(AccountLoginLogAddDTO logAddDTO) {
        AccountLoginLog loginLog = BeanUtils.copyProperties(logAddDTO, AccountLoginLog.class);
        loginLog.setLoginIp(ServletUtils.getRealIp());
        baseDaoT.insert(loginLog);
    }
}

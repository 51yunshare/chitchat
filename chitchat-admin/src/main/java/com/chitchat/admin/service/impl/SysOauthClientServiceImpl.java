package com.chitchat.admin.service.impl;

import com.chitchat.admin.bean.SysOauthClient;
import com.chitchat.admin.dao.SysOauthClientDaoI;
import com.chitchat.admin.service.SysOauthClientServiceI;
import com.chitchat.common.base.BaseServicesImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * OAuth2客户端业务实现类
 *
 * Created by Js on 2022/8/10.
 */
@Service
@RequiredArgsConstructor
public class SysOauthClientServiceImpl extends BaseServicesImpl<SysOauthClient, SysOauthClientDaoI> implements SysOauthClientServiceI {
    /**
     * 获取客户端信息
     *
     * @param clientId
     * @return
     */
    @Override
    public SysOauthClient getByClientId(String clientId) {
        return baseDaoT.getByClientId(clientId);
    }
}

package com.chitchat.admin.service;


import com.chitchat.admin.bean.SysOauthClient;
import com.chitchat.common.base.BaseServicesI;

/**
 * OAuth2客户端接口
 *
 * Created by Js on 2022/8/10.
 */
public interface SysOauthClientServiceI extends BaseServicesI<SysOauthClient> {
    /**
     * 获取客户端信息
     *
     * @param clientId
     * @return
     */
    SysOauthClient getByClientId(String clientId);
}

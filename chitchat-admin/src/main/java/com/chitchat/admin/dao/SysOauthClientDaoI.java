package com.chitchat.admin.dao;

import com.chitchat.admin.bean.SysOauthClient;
import com.chitchat.common.base.BaseDaoI;
import org.apache.ibatis.annotations.Param;

/**
 *
 * Created by Js on 2022/8/10.
 */
public interface SysOauthClientDaoI extends BaseDaoI<SysOauthClient> {

    /**
     * 获取客户端信息
     *
     * @param clientId
     * @return
     */
    SysOauthClient getByClientId(@Param("clientId") String clientId);
}

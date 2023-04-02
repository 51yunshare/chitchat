package com.chitchat.admin.service.impl;

import com.chitchat.admin.bean.SysBaseConfig;
import com.chitchat.admin.dao.SysBaseConfigDaoI;
import com.chitchat.admin.service.SysBaseConfigServiceI;
import com.chitchat.common.base.BaseServicesImpl;
import org.springframework.stereotype.Service;

/**
 * 配置信息
 *
 * Created by Js on 2022/12/2 .
 **/
@Service
public class SysBaseConfigServiceImpl extends BaseServicesImpl<SysBaseConfig, SysBaseConfigDaoI> implements SysBaseConfigServiceI {

    /**
     * Key获取配置信息
     *
     * @param configKey
     * @return
     */
    @Override
    public SysBaseConfig getByConfigKey(String configKey) {
        return baseDaoT.getByConfigKey(configKey);
    }
}

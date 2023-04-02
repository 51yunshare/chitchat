package com.chitchat.admin.service;

import com.chitchat.admin.bean.SysBaseConfig;
import com.chitchat.common.base.BaseServicesI;

/**
 * 配置信息
 *
 * Created by Js on 2022/12/2 .
 **/
public interface SysBaseConfigServiceI extends BaseServicesI<SysBaseConfig> {

    /**
     * Key获取配置信息
     *
     * @param configKey
     * @return
     */
    SysBaseConfig getByConfigKey(String configKey);
}

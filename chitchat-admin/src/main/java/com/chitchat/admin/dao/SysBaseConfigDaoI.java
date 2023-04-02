package com.chitchat.admin.dao;

import com.chitchat.admin.bean.SysBaseConfig;
import com.chitchat.common.base.BaseDaoI;
import org.apache.ibatis.annotations.Param;

public interface SysBaseConfigDaoI extends BaseDaoI<SysBaseConfig> {

    /**
     * Key获取配置信息
     *
     * @param configKey
     * @return
     */
    SysBaseConfig getByConfigKey(@Param("configKey") String configKey);
}

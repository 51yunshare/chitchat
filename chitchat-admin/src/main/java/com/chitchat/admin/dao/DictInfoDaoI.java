package com.chitchat.admin.dao;

import com.chitchat.admin.bean.DictInfo;
import com.chitchat.common.base.BaseDaoI;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DictInfoDaoI extends BaseDaoI<DictInfo> {

    /**
     * 根据类型获取字典信息
     *
     * @param type
     * @param pid
     * @return
     */
    List<DictInfo> getDictByTypeOrPid(@Param("type") Integer type, @Param("pid") Integer pid);
}
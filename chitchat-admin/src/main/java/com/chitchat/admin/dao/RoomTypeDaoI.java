package com.chitchat.admin.dao;

import com.chitchat.admin.bean.RoomType;
import com.chitchat.common.base.BaseDaoI;
import org.apache.ibatis.annotations.Param;

public interface RoomTypeDaoI extends BaseDaoI<RoomType> {

    /**
     * 名称获取房间类型
     *
     * @param typeName
     * @return
     */
    RoomType getByName(@Param("typeName") String typeName);
}

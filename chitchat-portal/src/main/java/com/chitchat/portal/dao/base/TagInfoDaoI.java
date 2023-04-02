package com.chitchat.portal.dao.base;

import com.chitchat.common.base.BaseDaoI;
import com.chitchat.portal.bean.base.TagInfo;
import org.apache.ibatis.annotations.Param;

/**
 * Created by Js on 2022/8/15 .
 **/
public interface TagInfoDaoI extends BaseDaoI<TagInfo> {

    /**
     * 获取标签
     *
     * @param tagName
     * @return
     */
    TagInfo getByName(@Param("tagName") String tagName);
}

package com.chitchat.portal.service.base;

import com.chitchat.common.base.BaseServicesI;
import com.chitchat.portal.bean.base.TagInfo;

/**
 * Created by Js on 2022/8/15 .
 **/
public interface TagInfoServiceI extends BaseServicesI<TagInfo> {

    /**
     * 获取标签
     *
     * @param tag
     * @return
     */
    TagInfo getByName(String tag);
}

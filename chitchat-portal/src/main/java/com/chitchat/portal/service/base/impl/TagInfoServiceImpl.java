package com.chitchat.portal.service.base.impl;

import com.chitchat.common.base.BaseServicesImpl;
import com.chitchat.portal.bean.base.TagInfo;
import com.chitchat.portal.dao.base.TagInfoDaoI;
import com.chitchat.portal.service.base.TagInfoServiceI;
import org.springframework.stereotype.Service;

/**
 * Created by Js on 2022/8/15 .
 **/
@Service
public class TagInfoServiceImpl extends BaseServicesImpl<TagInfo, TagInfoDaoI> implements TagInfoServiceI {
    /**
     * 获取标签
     *
     * @param tagName
     * @return
     */
    @Override
    public TagInfo getByName(String tagName) {
        return baseDaoT.getByName(tagName);
    }
}

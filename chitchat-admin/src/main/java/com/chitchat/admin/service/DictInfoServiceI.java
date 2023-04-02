package com.chitchat.admin.service;


import com.alibaba.fastjson.JSONArray;
import com.chitchat.admin.bean.DictInfo;
import com.chitchat.common.base.BaseServicesI;

/**
 * 字典
 *
 * Created by Js on 2022/8/10.
 */
public interface DictInfoServiceI extends BaseServicesI<DictInfo> {

    /**
     * 根据类型获取字典信息
     *
     * @param type
     * @return
     */
    JSONArray getDictByType(Integer type);
}

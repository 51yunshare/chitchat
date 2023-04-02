package com.chitchat.admin.service;

import com.alibaba.fastjson.JSONArray;
import com.chitchat.admin.bean.SysRole;
import com.chitchat.common.base.BaseServicesI;

/**
 * 用户角色
 *
 * Created by Js on 2022/12/1 .
 **/
public interface SysRoleServiceI extends BaseServicesI<SysRole> {

    /**
     * 用户角色下拉
     *
     * @return
     */
    JSONArray selectList();
}

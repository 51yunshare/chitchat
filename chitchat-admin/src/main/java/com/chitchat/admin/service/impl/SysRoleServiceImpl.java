package com.chitchat.admin.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chitchat.admin.bean.SysRole;
import com.chitchat.admin.dao.SysRoleDaoI;
import com.chitchat.admin.service.SysRoleServiceI;
import com.chitchat.common.base.BaseServicesImpl;
import com.chitchat.common.constant.SystemConstants;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 用户角色serviceImpl
 *
 * Created by Js on 2022/8/10.
 */
@Service
public class SysRoleServiceImpl extends BaseServicesImpl<SysRole, SysRoleDaoI> implements SysRoleServiceI {

    /**
     * 用户角色下拉(超级管理员移除)
     *
     * @return
     */
    @Override
    public JSONArray selectList() {
        List<SysRole> sysRoles = baseDaoT.list(null);
        JSONArray result = new JSONArray();
        sysRoles.forEach(role ->{
            if (!role.getCode().equals(SystemConstants.ROOT_ROLE_CODE)){
                result.add(new JSONObject()
                        .fluentPut("id", role.getId())
                        .fluentPut("roleName", role.getRoleName())
                        .fluentPut("code", role.getCode())
                );
            }
        });
        return result;
    }
}

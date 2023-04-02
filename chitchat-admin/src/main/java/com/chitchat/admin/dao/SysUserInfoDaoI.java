package com.chitchat.admin.dao;

import com.chitchat.admin.bean.SysUserInfo;
import com.chitchat.admin.bean.SysUserRole;
import com.chitchat.admin.vo.sys.UserAuthVO;
import com.chitchat.common.base.BaseDaoI;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 后台用户Dao
 *
 * Created by Js on 2022/8/10.
 */
public interface SysUserInfoDaoI extends BaseDaoI<SysUserInfo> {

    /**
     * 获取用户信息
     *
     * @param username
     * @return
     */
    UserAuthVO loadUserByUsername(@Param("username") String username);

    /**
     * 用户名唯一性
     *
     * @param username
     * @return
     */
    SysUserInfo checkUsernameUnique(@Param("username") String username);

    /**
     * 手机号唯一性
     *
     * @param mobile
     * @return
     */
    SysUserInfo checkMobileUnique(@Param("mobile") String mobile);

    /**
     * 新增用户角色信息
     *
     * @param userRoleList
     */
    void batchUserRole(@Param("userRoleList") List<SysUserRole> userRoleList);

    /**
     * 逻辑删除用户
     *
     * @param ids
     * @return
     */
    int updateBatchDelByIds(@Param("ids") List<Long> ids);
}

package com.chitchat.admin.service;


import com.chitchat.admin.bean.SysUserInfo;
import com.chitchat.admin.dto.SysUserAddRequestDTO;
import com.chitchat.admin.dto.SysUserLoginRequest;
import com.chitchat.admin.dto.SysUserUpdateRequestDTO;
import com.chitchat.admin.vo.sys.LoginUserVO;
import com.chitchat.admin.vo.sys.UserDetailVO;
import com.chitchat.common.base.BaseServicesI;
import com.chitchat.common.base.ResultTemplate;
import com.chitchat.common.base.UserDto;

/**
 * 后台用户接口
 *
 * Created by Js on 2022/8/10.
 */
public interface SysUserInfoServiceI extends BaseServicesI<SysUserInfo> {

    /**
     * 根据用户名获取通用用户信息
     *
     * @param username
     * @return
     */
    UserDto loadUserByUsername(String username);

    /**
     * 后台登录
     *
     * @param loginRequest
     * @return
     */
    ResultTemplate login(SysUserLoginRequest loginRequest);

    /**
     * 当前用户信息
     *
     * @return
     */
    LoginUserVO getLoginInfo();

    /**
     * 获取用户详情
     *
     * @param id
     */
    UserDetailVO getDetail(Long id);

    /**
     * 用户新增.
     *
     * @param addRequestDTO
     */
    void doAdd(SysUserAddRequestDTO addRequestDTO);

    /**
     * 修改用户
     *
     * @param updateRequestDTO
     * @return
     */
    void doUpdate(SysUserUpdateRequestDTO updateRequestDTO);

    /**
     * 删除用户
     *
     * @param ids
     * @return
     */
    void deleteUsers(String ids);

    /**
     * 修改用户密码
     *
     * @param userId
     * @param password
     */
    void updateUserPassword(Long userId, String password);

    /**
     * 修改用户状态
     *
     * @param userId
     * @param status
     * @return
     */
    void updateUserStatus(Long userId, Integer status);
}

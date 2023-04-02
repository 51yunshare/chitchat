package com.chitchat.admin.service.impl;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.BCrypt;
import com.chitchat.admin.bean.SysMenu;
import com.chitchat.admin.bean.SysUserInfo;
import com.chitchat.admin.bean.SysUserRole;
import com.chitchat.admin.dao.SysUserInfoDaoI;
import com.chitchat.admin.dto.SysUserAddRequestDTO;
import com.chitchat.admin.dto.SysUserLoginRequest;
import com.chitchat.admin.dto.SysUserUpdateRequestDTO;
import com.chitchat.admin.service.SysMenuServiceI;
import com.chitchat.admin.service.SysRoleServiceI;
import com.chitchat.admin.service.SysUserInfoServiceI;
import com.chitchat.admin.vo.sys.LoginUserVO;
import com.chitchat.admin.vo.sys.UserAuthVO;
import com.chitchat.admin.vo.sys.UserDetailVO;
import com.chitchat.auth.api.feign.FeignAuthServiceI;
import com.chitchat.common.base.BaseServicesImpl;
import com.chitchat.common.base.CodeMsg;
import com.chitchat.common.base.ResultTemplate;
import com.chitchat.common.base.UserDto;
import com.chitchat.common.constant.AuthConstant;
import com.chitchat.common.exception.ChitchatException;
import com.chitchat.common.util.BeanUtils;
import com.chitchat.common.web.jwt.UserUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 后台用户serviceImpl
 * <p>
 * Created by Js on 2022/8/10.
 */
@Service
@RequiredArgsConstructor
public class SysUserInfoServiceImpl extends BaseServicesImpl<SysUserInfo, SysUserInfoDaoI> implements SysUserInfoServiceI {

    private final FeignAuthServiceI feignAuthServiceI;
    private final SysMenuServiceI sysMenuServiceI;
    private final SysRoleServiceI sysRoleServiceI;

    /**
     * 根据用户名获取通用用户信息
     *
     * @param username
     * @return
     */
    @Override
    public UserDto loadUserByUsername(String username) {
        UserAuthVO userAuthVO = baseDaoT.loadUserByUsername(username);
        if (userAuthVO != null) {
            return BeanUtils.copyProperties(userAuthVO, UserDto.class);
        }
        return null;
    }

    /**
     * 后台登录
     *
     * @param loginRequest
     * @return
     */
    @Override
    public ResultTemplate login(SysUserLoginRequest loginRequest) {
        Map<String, String> params = new HashMap<>();
        params.put("client_id", AuthConstant.ADMIN_CLIENT_ID);
        params.put("client_secret", AuthConstant.ADMIN_CLIENT_SECRET);
        params.put("grant_type", "password");
        params.put("username", loginRequest.getUsername());
        params.put("password", loginRequest.getPassword());
        return feignAuthServiceI.getAccessToken(params);
    }

    /**
     * 当前用户信息
     *
     * @return
     */
    @Override
    public LoginUserVO getLoginInfo() {
        UserDto userDto = UserUtils.getUser();
        SysUserInfo userInfo = baseDaoT.getById(userDto.getId());
        LoginUserVO loginUserVO = BeanUtils.copyProperties(userInfo, LoginUserVO.class);
        // 用户角色集合
        List<String> roles = UserUtils.getRoles();
        loginUserVO.setRoles(roles);

        // 用户按钮权限集合
        List<SysMenu> menuList = sysMenuServiceI.listMenuByUserId(userDto.getId());
        if (ObjectUtil.length(menuList) > 0) {
            loginUserVO.setPerms(menuList.stream().filter(menu -> StrUtil.isNotBlank(menu.getPermission())).map(SysMenu::getPermission).collect(Collectors.toList()));
        }
        return loginUserVO;
    }

    /**
     * 获取用户详情
     *
     * @param id
     */
    @Override
    public UserDetailVO getDetail(Long id) {
        SysUserInfo sysUserInfo = this.getById(id, "用户信息");
        return BeanUtils.copyProperties(sysUserInfo, UserDetailVO.class);
    }

    /**
     * 用户新增
     *
     * @param dto
     */
    @Override
    @Transactional
    public void doAdd(SysUserAddRequestDTO dto) {
        //校验唯一性
        this.checkUsernameUnique(dto.getUsername(), -1L);
        this.checkMobileUnique(dto.getMobile(), -1L);
        //用户角色判断
        if (ObjectUtil.length(dto.getRoleIds()) > 0) {
            Arrays.stream(dto.getRoleIds()).map(role -> sysRoleServiceI.getById(role, "部分角色信息"));
        }
        SysUserInfo userInfo = BeanUtils.copyProperties(dto, SysUserInfo.class);
        userInfo.setPassword(BCrypt.hashpw(userInfo.getPassword()));
        userInfo.setCreator(UserUtils.getUsername());
        baseDaoT.insert(userInfo);
        //用户角色新增
        this.insertUserRole(userInfo.getId(), dto.getRoleIds());
    }

    /**
     * 修改用户
     *
     * @param dto
     * @return
     */
    @Override
    @Transactional
    public void doUpdate(SysUserUpdateRequestDTO dto) {
        //判断用户是否存在
        this.getById(dto.getId(), "用户信息");
        //校验唯一性
        this.checkUsernameUnique(dto.getUsername(), dto.getId());
        this.checkMobileUnique(dto.getMobile(), dto.getId());
        SysUserInfo userInfo = BeanUtils.copyProperties(dto, SysUserInfo.class);
        baseDaoT.updateByPrimaryKeySelective(userInfo);
        //用户角色判断
        if (ObjectUtil.length(dto.getRoleIds()) > 0) {
            Arrays.stream(dto.getRoleIds()).map(role -> sysRoleServiceI.getById(role, "部分角色信息"));
            //用户角色新增
            this.insertUserRole(userInfo.getId(), dto.getRoleIds());
        }
    }

    /**
     * 删除用户
     *
     * @param idsStr
     * @return
     */
    @Override
    @Transactional
    public void deleteUsers(String idsStr) {
        Assert.isTrue(StrUtil.isNotBlank(idsStr), "删除的用户数据为空");
        // 逻辑删除
        List<Long> ids = Arrays.asList(idsStr.split(",")).stream()
                .map(idStr -> Long.parseLong(idStr)).collect(Collectors.toList());
        ids.forEach(id ->{
            this.getById(id, "部分用户信息");
        });
        baseDaoT.updateBatchDelByIds(ids);
    }

    /**
     * 修改用户密码
     *
     * @param userId
     * @param password
     */
    @Override
    @Transactional
    public void updateUserPassword(Long userId, String password) {
        //判断用户是否存在
        this.getById(userId, "用户信息");
        baseDaoT.updateByPrimaryKeySelective(SysUserInfo.builder()
                .id(userId)
                .password(BCrypt.hashpw(password))
                .build());
    }

    /**
     * 修改用户状态
     *
     * @param userId
     * @param status
     * @return
     */
    @Override
    @Transactional
    public void updateUserStatus(Long userId, Integer status) {
        //判断用户是否存在
        this.getById(userId, "用户信息");
        baseDaoT.updateByPrimaryKeySelective(SysUserInfo.builder()
                .id(userId)
                .status(status)
                .build());
    }

    /**
     * 新增用户角色信息
     *
     * @param userId  用户ID
     * @param roleIds 角色组
     */
    private void insertUserRole(Long userId, Long[] roleIds) {
        if (ObjectUtil.length(roleIds) > 0) {
            // 新增用户与角色管理
            List<SysUserRole> list = new ArrayList<>();
            Arrays.stream(roleIds).forEach(roleId ->{
                list.add(SysUserRole.builder().userId(userId).roleId(roleId).build());
            });
            baseDaoT.batchUserRole(list);
        }
    }

    /**
     * 手机号唯一性
     *
     * @param mobile
     * @param userId
     */
    private void checkMobileUnique(String mobile, Long userId) {
        if (StrUtil.isNotBlank(mobile)) {
            SysUserInfo sysUserByMobile = baseDaoT.checkMobileUnique(mobile);
            if (sysUserByMobile != null && sysUserByMobile.getId().longValue() != userId.longValue()) {
                throw new ChitchatException(CodeMsg.BIND_ERROR, "Mobile phone number has been in exists");
            }
        }
    }

    /**
     * 登录名唯一性
     *
     * @param username
     */
    private void checkUsernameUnique(String username, Long userId) {
        SysUserInfo sysUserInfo = baseDaoT.checkUsernameUnique(username);
        if (sysUserInfo != null && sysUserInfo.getId().longValue() != userId.longValue()) {
            throw new ChitchatException(CodeMsg.BIND_ERROR, "The user login name already exists！");
        }
    }
}

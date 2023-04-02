package com.chitchat.auth.service.impl;

import com.chitchat.admin.api.feign.FeignSysUserServiceI;
import com.chitchat.auth.bean.SecurityUser;
import com.chitchat.common.base.CodeMsg;
import com.chitchat.common.base.UserDto;
import com.chitchat.common.constant.AuthConstant;
import com.chitchat.common.util.ServletUtils;
import com.chitchat.portal.api.feign.FeignPortalAccountServiceI;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * 用户管理业务类(实现Security 的 UserDetailsService)
 * <p>
 * Created by Js on 2022/7/31.
 **/
@Service
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private FeignSysUserServiceI sysUserServiceI;
    @Autowired
    private FeignPortalAccountServiceI accountServiceI;

    /**
     * 登录方法
     * 前后台用户登录：需要loginType判断
     *
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        String clientId = ServletUtils.getOAuth2ClientId();
        UserDto userDto;
        if (AuthConstant.ADMIN_CLIENT_ID.equals(clientId)) {
            //后台用户
            userDto = sysUserServiceI.loadUserByUsername(username);
            //todo 用户权限

        } else {
            userDto = accountServiceI.loadUserByUsername(username);
        }
        if (userDto == null) {
            throw new UsernameNotFoundException(CodeMsg.USERNAME_PASSWORD_ERROR.getMsg());
        }
        userDto.setClientId(clientId);
        SecurityUser securityUser = new SecurityUser(userDto);
        if (!securityUser.isEnabled()) {
            throw new DisabledException(CodeMsg.ACCOUNT_DISABLED.getMsg());
        } else if (!securityUser.isAccountNonLocked()) {
            throw new LockedException(CodeMsg.ACCOUNT_LOCKED.getMsg());
        } else if (!securityUser.isAccountNonExpired()) {
            throw new AccountExpiredException(CodeMsg.ACCOUNT_EXPIRED.getMsg());
        } else if (!securityUser.isCredentialsNonExpired()) {
            throw new CredentialsExpiredException(CodeMsg.CREDENTIALS_EXPIRED.getMsg());
        }
        return securityUser;
    }

    /**
     * openid 认证方式
     *
     * @param openId
     * @return
     */
    public UserDetails loadUserByOpenId(String openId, Integer identityType) {
        SecurityUser userDetails = null;
        UserDto userDto = accountServiceI.loadUserByOpenId(openId, identityType);

        if (userDto != null) {
            userDetails = new SecurityUser(userDto);
//            userDetails.setAuthenticationIdentity(AuthenticationIdentityEnum.OPENID.getValue());   // 认证身份标识:openId

        }
        if (userDetails == null) {
            throw new UsernameNotFoundException(CodeMsg.USER_NOT_EXIST.getMsg());
        } else if (!userDetails.isEnabled()) {
            throw new DisabledException(CodeMsg.ACCOUNT_DISABLED.getMsg());
        } else if (!userDetails.isAccountNonLocked()) {
            throw new LockedException(CodeMsg.ACCOUNT_LOCKED.getMsg());
        } else if (!userDetails.isAccountNonExpired()) {
            throw new AccountExpiredException(CodeMsg.ACCOUNT_EXPIRED.getMsg());
        }
        return userDetails;
    }

    /**
     * 手机号码认证方式
     *
     * @param mobile
     * @return
     */
    public UserDetails loadUserByMobile(String mobile) {
        UserDto userDto = accountServiceI.loadUserByMobile(mobile);
        if (userDto == null) {
            throw new UsernameNotFoundException(CodeMsg.USERNAME_PASSWORD_ERROR.getMsg());
        }
//        userDto.setClientId(clientId);
        SecurityUser userDetails = new SecurityUser(userDto);
        if (!userDetails.isEnabled()) {
            throw new DisabledException(CodeMsg.ACCOUNT_DISABLED.getMsg());
        } else if (!userDetails.isAccountNonLocked()) {
            throw new LockedException(CodeMsg.ACCOUNT_LOCKED.getMsg());
        } else if (!userDetails.isAccountNonExpired()) {
            throw new AccountExpiredException(CodeMsg.ACCOUNT_EXPIRED.getMsg());
        } else if (!userDetails.isCredentialsNonExpired()) {
            throw new CredentialsExpiredException(CodeMsg.CREDENTIALS_EXPIRED.getMsg());
        }
        return userDetails;
    }
}

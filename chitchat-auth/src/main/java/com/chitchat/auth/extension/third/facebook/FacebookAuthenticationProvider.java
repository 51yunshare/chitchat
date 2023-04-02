package com.chitchat.auth.extension.third.facebook;

import com.chitchat.auth.config.justauth.EnumLoginAppType;
import com.chitchat.auth.config.justauth.JustAuthUtil;
import com.chitchat.auth.service.impl.UserDetailsServiceImpl;
import com.chitchat.common.base.UserDto;
import com.chitchat.portal.api.dto.MemberDTO;
import com.chitchat.portal.api.feign.FeignPortalAccountServiceI;
import lombok.Data;
import lombok.SneakyThrows;
import me.zhyd.oauth.model.AuthResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.HashSet;

/**
 * Facebook认证提供者
 *
 * Created by Js on 2022/8/11.
 */
@Data
public class FacebookAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private FeignPortalAccountServiceI accountServiceI;
    @Autowired
    private JustAuthUtil justAuthUtil;

    /**
     * Facebook认证
     *
     * @param authentication
     * @return
     * @throws AuthenticationException
     */
    @SneakyThrows
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        FacebookAuthenticationToken authenticationToken = (FacebookAuthenticationToken) authentication;
        String code = (String) authenticationToken.getPrincipal();
        String state = authenticationToken.getState();
        //通过code获取Facebook个人信息
        // 创建授权request
        AuthResponse<FacebookUserInfo> authResponse = justAuthUtil.loginByType(code, state, EnumLoginAppType.FACEBOOK);
        String openid = authResponse.getData().getUuid();
        UserDto user = accountServiceI.loadUserByOpenId(openid, EnumLoginAppType.FACEBOOK.getIndex());
        // facebook 用户不存在，注册成为新会员
        if (user == null) {
            MemberDTO memberDTO = new MemberDTO();
            memberDTO.setIdentifier(openid);
            accountServiceI.addMember(memberDTO);
        }
        UserDetails userDetails = ((UserDetailsServiceImpl) userDetailsService).loadUserByOpenId(openid, EnumLoginAppType.FACEBOOK.getIndex());
        FacebookAuthenticationToken result = new FacebookAuthenticationToken(userDetails, new HashSet<>());
        result.setDetails(authentication.getDetails());
        return result;
    }


    @Override
    public boolean supports(Class<?> authentication) {
        return FacebookAuthenticationToken.class.isAssignableFrom(authentication);
    }
}

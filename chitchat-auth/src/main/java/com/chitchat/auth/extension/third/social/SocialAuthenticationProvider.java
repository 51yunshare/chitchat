package com.chitchat.auth.extension.third.social;

import cn.hutool.core.util.IdUtil;
import com.chitchat.auth.config.justauth.JustAuthUtil;
import com.chitchat.auth.service.impl.UserDetailsServiceImpl;
import com.chitchat.common.base.UserDto;
import com.chitchat.common.enumerate.EnumYesOrNo;
import com.chitchat.portal.api.dto.MemberDTO;
import com.chitchat.portal.api.feign.FeignPortalAccountServiceI;
import lombok.Data;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.HashSet;

/**
 * 社交平台认证提供者(google/facebook)
 *
 * Created by Js on 2022/11/5.
 */
@Data
@Slf4j
public class SocialAuthenticationProvider implements AuthenticationProvider {

    private UserDetailsService userDetailsService;
    private FeignPortalAccountServiceI accountServiceI;
    @Autowired
    private JustAuthUtil justAuthUtil;

    /**
     * 社交平台认证
     *
     * @param authentication
     * @return
     * @throws AuthenticationException
     */
    @SneakyThrows
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        SocialAuthenticationToken authenticationToken = (SocialAuthenticationToken) authentication;
        String identifier = (String) authenticationToken.getPrincipal();
        Integer identityType = Integer.valueOf(authenticationToken.getIdentityType());
        UserDto user = accountServiceI.loadUserByOpenId(identifier, identityType);
        // google用户不存在，注册成为新会员
        if (user == null) {
            MemberDTO memberDTO = new MemberDTO();
//            BeanUtil.copyProperties(userInfo, memberDTO);
            memberDTO.setIdentifier(identifier);
            memberDTO.setNickName(IdUtil.fastSimpleUUID());
            memberDTO.setStatus(EnumYesOrNo.是.getIndex());
            memberDTO.setAppType(identityType);
            accountServiceI.addMember(memberDTO);
        }
        UserDetails userDetails = ((UserDetailsServiceImpl) userDetailsService).loadUserByOpenId(identifier, identityType);
        SocialAuthenticationToken result = new SocialAuthenticationToken(userDetails, new HashSet<>());
        result.setDetails(authentication.getDetails());
        return result;
    }


    @Override
    public boolean supports(Class<?> authentication) {
        return SocialAuthenticationToken.class.isAssignableFrom(authentication);
    }
}

package com.chitchat.auth.extension.third.google;

import cn.hutool.json.JSONUtil;
import com.chitchat.auth.config.justauth.EnumLoginAppType;
import com.chitchat.auth.config.justauth.JustAuthUtil;
import com.chitchat.auth.service.impl.UserDetailsServiceImpl;
import com.chitchat.common.base.UserDto;
import com.chitchat.common.enumerate.EnumYesOrNo;
import com.chitchat.portal.api.dto.MemberDTO;
import com.chitchat.portal.api.feign.FeignPortalAccountServiceI;
import lombok.Data;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import me.zhyd.oauth.model.AuthResponse;
import me.zhyd.oauth.model.AuthUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.HashSet;

/**
 * Google认证提供者
 *
 * Created by Js on 2022/8/11.
 */
@Data
@Slf4j
public class GoogleAuthenticationProvider implements AuthenticationProvider {

    private UserDetailsService userDetailsService;
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
        GoogleAuthenticationToken authenticationToken = (GoogleAuthenticationToken) authentication;
        String code = (String) authenticationToken.getPrincipal();
        //通过code获取Google个人信息
        // 授权登录
        AuthResponse<AuthUser> authResponse = justAuthUtil.loginByType(code, EnumLoginAppType.GOOGLE);
        log.info(">>>>>>>>>>>登录成功：{}", JSONUtil.toJsonStr(authResponse));
        String openid = authResponse.getData().getUuid();
        UserDto user = accountServiceI.loadUserByOpenId(openid, EnumLoginAppType.GOOGLE.getIndex());
        // google用户不存在，注册成为新会员
        if (user == null) {
            MemberDTO memberDTO = new MemberDTO();
//            BeanUtil.copyProperties(userInfo, memberDTO);
            memberDTO.setIdentifier(openid);
            memberDTO.setNickName(authResponse.getData().getNickname());
            memberDTO.setIcon(authResponse.getData().getAvatar());
            memberDTO.setStatus(EnumYesOrNo.是.getIndex());
            memberDTO.setAppType(EnumLoginAppType.GOOGLE.getIndex());
            accountServiceI.addMember(memberDTO);
        }
        UserDetails userDetails = ((UserDetailsServiceImpl) userDetailsService).loadUserByOpenId(openid, EnumLoginAppType.GOOGLE.getIndex());
        GoogleAuthenticationToken result = new GoogleAuthenticationToken(userDetails, new HashSet<>());
        result.setDetails(authentication.getDetails());
        return result;
    }


    @Override
    public boolean supports(Class<?> authentication) {
        return GoogleAuthenticationToken.class.isAssignableFrom(authentication);
    }
}

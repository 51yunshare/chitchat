package com.chitchat.auth.extension.mobile;

import cn.hutool.crypto.digest.BCrypt;
import com.chitchat.auth.service.impl.UserDetailsServiceImpl;
import com.chitchat.common.base.UserDto;
import com.chitchat.common.enumerate.EnumYesOrNo;
import com.chitchat.portal.api.dto.MemberDTO;
import com.chitchat.portal.api.feign.FeignPortalAccountServiceI;
import lombok.Data;
import lombok.SneakyThrows;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.HashSet;

/**
 * 手机号密码认证授权提供者
 *
 * 这个类就是真正的处理类，经过TokenGranter后，会找到对应的AuthenticationProvider，
 * 然后取出参数从数据库（UserDetailService）中查询对应的信息进行匹配
 *
 * Created by Js on 2022/8/14.
 */
@Data
public class MobilePasswordAuthenticationProvider implements AuthenticationProvider {

    private UserDetailsService userDetailsService;
    private FeignPortalAccountServiceI accountServiceI;


    @SneakyThrows
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        MobilePasswordAuthenticationToken authenticationToken = (MobilePasswordAuthenticationToken) authentication;
        String mobile = (String) authenticationToken.getPrincipal();
        String password = (String) authenticationToken.getCredentials();
        //查询手机号是否存在
        UserDto userDto = accountServiceI.loadUserByMobile(mobile);
        if (userDto == null){
            //注册信息
            accountServiceI.addMember(new MemberDTO()
                    .setUsername(mobile)
                    .setMobile(mobile)
                    .setPassword(BCrypt.hashpw(password))
                    .setStatus(EnumYesOrNo.是.getIndex()));
        }
        //查询数据库
        UserDetails userDetails = ((UserDetailsServiceImpl) userDetailsService).loadUserByMobile(mobile);
        MobilePasswordAuthenticationToken result = new MobilePasswordAuthenticationToken(userDetails, authentication.getCredentials(), new HashSet<>());
        result.setDetails(authentication.getDetails());
        return result;
    }

    /**
     * Spring security 会通过这个方法判断当前provider是否需要处理
     *
     * @param authentication
     * @return
     */
    @Override
    public boolean supports(Class<?> authentication) {
        return MobilePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}

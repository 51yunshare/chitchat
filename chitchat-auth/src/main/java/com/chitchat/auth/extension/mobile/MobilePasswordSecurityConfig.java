package com.chitchat.auth.extension.mobile;

import com.chitchat.portal.api.feign.FeignPortalAccountServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.stereotype.Component;

@Component
public class MobilePasswordSecurityConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    @Autowired
    private UserDetailsService userDetailService;
    @Autowired
    private FeignPortalAccountServiceI accountServiceI;

    /**
     * 手机号密码配置器
     *
     *  所有的配置都可以移步到WebSecurityConfig
     *  builder.authenticationProvider() 相当于 auth.authenticationProvider();
     *  使用外部配置必须要在WebSecurityConfig中用http.apply(MobilePasswordSecurityConfig)将配置注入进去
     */
    @Override
    public void configure(HttpSecurity builder) {
        //注入MobilePasswordAuthenticationProvider
        MobilePasswordAuthenticationProvider mobilePasswordAuthenticationProvider = new MobilePasswordAuthenticationProvider();
        mobilePasswordAuthenticationProvider.setUserDetailsService(userDetailService);
        mobilePasswordAuthenticationProvider.setAccountServiceI(accountServiceI);
        builder.authenticationProvider(mobilePasswordAuthenticationProvider);
    }
}
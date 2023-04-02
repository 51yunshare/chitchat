package com.chitchat.auth.config;

import com.chitchat.auth.extension.mobile.MobilePasswordSecurityConfig;
import com.chitchat.auth.extension.third.facebook.FacebookAuthenticationProvider;
import com.chitchat.auth.extension.third.google.GoogleAuthenticationProvider;
import com.chitchat.auth.extension.third.social.SocialAuthenticationProvider;
import com.chitchat.auth.service.impl.UserDetailsServiceImpl;
import com.chitchat.portal.api.feign.FeignPortalAccountServiceI;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * SpringSecurity配置
 *
 * Created by Js on 2022/7/30.
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsServiceImpl userDetailsService;
    //自定义手机号密码授权模式
    private final MobilePasswordSecurityConfig mobilePasswordSecurityConfig;
    private final FeignPortalAccountServiceI accountServiceI;

    /**
     * Security接口拦截配置(用于配置需要拦截的url路径、jwt过滤器及出异常后的处理器)
     * 过滤登录、文档
     *
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .apply(mobilePasswordSecurityConfig)
                .and()
                .authorizeRequests()
                .requestMatchers(EndpointRequest.toAnyEndpoint()).permitAll()
                .antMatchers("/rsa/publicKey").permitAll()
                // @link https://gitee.com/xiaoym/knife4j/issues/I1Q5X6 (接口文档knife4j需要放行的规则)
                .antMatchers("/webjars/**", "/doc.html", "/swagger-resources/**", "/v2/api-docs").permitAll()
                .antMatchers("/oauth/**", "/sms/send").permitAll()
                .anyRequest().authenticated()
                .and()
                //关闭csrf
                .csrf().disable();
    }

    /**
     * 暴露认证方式
     *
     * @return
     * @throws Exception
     */
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    /**
     * 授权模式 用户密码、微信、手机号验证码
     *
     * @param auth
     */
    @Override
    public void configure(AuthenticationManagerBuilder auth) {
        auth
                .authenticationProvider(daoAuthenticationProvider())
                .authenticationProvider(facebookAuthenticationProvider())
                .authenticationProvider(googleAuthenticationProvider())
                .authenticationProvider(socialAuthenticationProvider())
        ;
    }

    /**
     * 加密器
     *
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 用户名密码认证授权提供者
     *
     * @return
     */
    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        provider.setHideUserNotFoundExceptions(false); // 是否隐藏用户不存在异常，默认:true-隐藏；false-抛出异常；
        return provider;
    }

    /**
     * facebook 认证
     *
     * @return
     */
    @Bean
    public FacebookAuthenticationProvider facebookAuthenticationProvider(){
        FacebookAuthenticationProvider provider = new FacebookAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setAccountServiceI(accountServiceI);
        return provider;
    }

    /**
     * Google认证
     *
     * @return
     */
    @Bean
    public GoogleAuthenticationProvider googleAuthenticationProvider(){
        GoogleAuthenticationProvider provider = new GoogleAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setAccountServiceI(accountServiceI);
        return provider;
    }

    /**
     * 社交平台认证
     *
     * @return
     */
    @Bean
    public SocialAuthenticationProvider socialAuthenticationProvider(){
        SocialAuthenticationProvider provider = new SocialAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setAccountServiceI(accountServiceI);
        return provider;
    }
}

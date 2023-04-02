package com.chitchat.auth.component;

import com.chitchat.auth.bean.SecurityUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * jwtToken增强器(主要是给token添加额外信息)
 * Created by Js on 2022/8/1 .
 **/
@Component
@Slf4j
public class JwtTokenEnhancer implements TokenEnhancer {

    /**
     * 给token中添加下游需要的信息(用户id)
     *
     * @param accessToken
     * @param authentication
     * @return
     */
    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        log.info(">>>>> 认证中心->Token增强器 <<<<<");
        SecurityUser securityUser = (SecurityUser) authentication.getPrincipal();
        Map<String, Object> info = new HashMap<>();
        //把用户ID设置到JWT中
        info.put("id", securityUser.getId());
        //过滤器判断是否哪个端登录的
        info.put("client_id", securityUser.getClientId());
        info.put("nickName", securityUser.getNickName());
        info.put("gender", securityUser.getGender());
        info.put("country", securityUser.getCountry());
        ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(info);
        return accessToken;
    }

}

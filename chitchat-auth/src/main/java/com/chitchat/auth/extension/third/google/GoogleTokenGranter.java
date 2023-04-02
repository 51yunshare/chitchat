package com.chitchat.auth.extension.third.google;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.common.exceptions.InvalidGrantException;
import org.springframework.security.oauth2.provider.*;
import org.springframework.security.oauth2.provider.token.AbstractTokenGranter;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 *  Google授权者
 *
 * Created by Js on 2022/8/11.
 */
public class GoogleTokenGranter extends AbstractTokenGranter {

    /**
     * 声明授权者 WechatTokenGranter 支持授权模式 wechat
     * 根据接口传值 grant_type = wechat 的值匹配到此授权者
     * 匹配逻辑详见下面的两个方法
     *
     * @see CompositeTokenGranter#grant(String, TokenRequest)
     * @see AbstractTokenGranter#grant(String, TokenRequest)
     */
    private static final String GRANT_TYPE = "google";
    private final AuthenticationManager authenticationManager;

    public GoogleTokenGranter(AuthorizationServerTokenServices tokenServices, ClientDetailsService clientDetailsService, OAuth2RequestFactory requestFactory, AuthenticationManager authenticationManager) {
        super(tokenServices, clientDetailsService, requestFactory, GRANT_TYPE);
        this.authenticationManager = authenticationManager;
    }

    @Override
    protected OAuth2Authentication getOAuth2Authentication(ClientDetails client, TokenRequest tokenRequest) {

        Map<String, String> parameters = new LinkedHashMap(tokenRequest.getRequestParameters());
        String code = parameters.get("code");
        String state = parameters.get("state");

        // 移除后续无用参数
        parameters.remove("code");
        parameters.remove("state");

        Authentication userAuth = new GoogleAuthenticationToken(code, state); // 未认证状态
        ((AbstractAuthenticationToken) userAuth).setDetails(parameters);

        try {
            userAuth = this.authenticationManager.authenticate(userAuth); // 认证中
        } catch (Exception e) {
            throw new InvalidGrantException(e.getMessage());
        }

        if (userAuth != null && userAuth.isAuthenticated()) { // 认证成功
            OAuth2Request storedOAuth2Request = this.getRequestFactory().createOAuth2Request(client, tokenRequest);
            return new OAuth2Authentication(storedOAuth2Request, userAuth);
        } else { // 认证失败
            throw new InvalidGrantException("Could not authenticate code: " + code);
        }
    }
}

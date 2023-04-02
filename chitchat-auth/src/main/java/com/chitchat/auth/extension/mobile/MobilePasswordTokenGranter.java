package com.chitchat.auth.extension.mobile;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.AccountStatusException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.common.exceptions.InvalidGrantException;
import org.springframework.security.oauth2.provider.*;
import org.springframework.security.oauth2.provider.token.AbstractTokenGranter;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 手机号密码授权者
 * 每种授权类型都对应一种TokenGranter，
 * 其中会定义授权类型的名称，比如密码模式的ResourceOwnerPasswordTokenGranter，其中的GRANT_TYPE为password
 * <p>
 * Created by Js on 2022/8/14.
 */
public class MobilePasswordTokenGranter extends AbstractTokenGranter {

    /**
     * 声明授权者 MobilePasswordTokenGranter 支持授权模式 mobile_pwd
     * 根据接口传值 grant_type = mobile_pwd 的值匹配到此授权者
     * 匹配逻辑详见下面的两个方法
     *
     * @see CompositeTokenGranter#grant(String, TokenRequest)
     * @see AbstractTokenGranter#grant(String, TokenRequest)
     */
    private static final String GRANT_TYPE = "mobile_pwd";
    private final AuthenticationManager authenticationManager;

    public MobilePasswordTokenGranter(AuthorizationServerTokenServices tokenServices, ClientDetailsService clientDetailsService,
                                      OAuth2RequestFactory requestFactory, AuthenticationManager authenticationManager) {

        super(tokenServices, clientDetailsService, requestFactory, GRANT_TYPE);
        this.authenticationManager = authenticationManager;
    }

    @Override
    protected OAuth2Authentication getOAuth2Authentication(ClientDetails client, TokenRequest tokenRequest) {

        Map<String, String> parameters = new LinkedHashMap(tokenRequest.getRequestParameters());

        String mobile = parameters.get("mobile"); // 手机号
        String pwd = parameters.get("password"); // 密码
        //将密码移除
        parameters.remove("password");

        Authentication userAuth = new MobilePasswordAuthenticationToken(mobile, pwd);
        ((AbstractAuthenticationToken) userAuth).setDetails(parameters);

        try {
            //封装后的AuthenticationToken，交给认证管理器
            userAuth = this.authenticationManager.authenticate(userAuth);
        } catch (AccountStatusException var8) {
            throw new InvalidGrantException(var8.getMessage());
        } catch (BadCredentialsException var9) {
            throw new InvalidGrantException(var9.getMessage());
        }

        if (userAuth != null && userAuth.isAuthenticated()) {
            OAuth2Request storedOAuth2Request = this.getRequestFactory().createOAuth2Request(client, tokenRequest);
            return new OAuth2Authentication(storedOAuth2Request, userAuth);
        } else {
            throw new InvalidGrantException("Could not authenticate user: " + mobile);
        }
    }
}

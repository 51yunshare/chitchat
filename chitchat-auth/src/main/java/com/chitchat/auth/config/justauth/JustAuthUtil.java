package com.chitchat.auth.config.justauth;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.chitchat.common.base.CodeMsg;
import com.chitchat.common.exception.ChitchatException;
import com.xkcoding.http.config.HttpConfig;
import lombok.RequiredArgsConstructor;
import me.zhyd.oauth.config.AuthConfig;
import me.zhyd.oauth.model.AuthCallback;
import me.zhyd.oauth.model.AuthResponse;
import me.zhyd.oauth.request.AuthFacebookRequest;
import me.zhyd.oauth.request.AuthGoogleRequest;
import me.zhyd.oauth.request.AuthRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.Map;

/**
 * Created by Js on 2022/8/25 .
 **/
@Component
@RequiredArgsConstructor
public class JustAuthUtil {

    private final JustAuthConfig justAuthConfig;

    public AuthResponse loginByType(String code, String state, EnumLoginAppType appType) {
        // 创建授权request
        AuthRequest authRequest = getDefaultRequest(appType.getName());
        if (authRequest == null){
            throw new ChitchatException(CodeMsg.SERVER_ERROR, "第三方App配置有误！");
        }
        if (StrUtil.isBlank(state)){
            String uuid = IdUtil.simpleUUID();
            authRequest.authorize(uuid);
            return authRequest.login(AuthCallback.builder().code(code).state(uuid).build());
        }
        return authRequest.login(AuthCallback.builder().code(code).state(state).build());
    }

    public AuthResponse loginByType(String code, EnumLoginAppType appType) {
        return loginByType(code, null, appType);
    }

    /**
     * 获取对应的请求体
     *
     * @param source
     * @return
     */
    private AuthRequest getDefaultRequest(String source) {
        EnumLoginAppType enumLoginAppType;
        try {
            enumLoginAppType = EnumLoginAppType.getByName(source);
        } catch (IllegalArgumentException e) {
            // 无自定义匹配
            return null;
        }

        AuthConfig config = justAuthConfig.getType().get(enumLoginAppType.getName());
        // 找不到对应关系，直接返回空
        if (config == null) {
            return null;
        }
//        System.out.println(JSON.toJSONString(justAuthConfig));
        // 配置 http config
        this.configureHttpConfig(enumLoginAppType.getName(), config, justAuthConfig.getHttpConfig());
        switch (enumLoginAppType) {
            case GOOGLE:
                return new AuthGoogleRequest(config);
            case FACEBOOK:
                return new AuthFacebookRequest(config);
            default:
                return null;
        }
    }

    /**
     * 配置 http 相关的配置
     */
    private void configureHttpConfig(String authSource, AuthConfig authConfig, JustAuthConfig.JustAuthHttpConfig httpConfig) {
        if (null == httpConfig) {
            return;
        }
        Map<String, JustAuthConfig.JustAuthProxyConfig> proxyConfigMap = httpConfig.getProxy();
        if (CollectionUtils.isEmpty(proxyConfigMap)) {
            return;
        }
        JustAuthConfig.JustAuthProxyConfig proxyConfig = proxyConfigMap.get(authSource);
        if (null == proxyConfig) {
            return;
        }

        authConfig.setHttpConfig(HttpConfig.builder()
                .timeout(httpConfig.getTimeout())
                .proxy(new Proxy(Proxy.Type.valueOf(proxyConfig.getType()), new InetSocketAddress(proxyConfig.getHostname(), proxyConfig.getPort())))
                .build());
    }
}

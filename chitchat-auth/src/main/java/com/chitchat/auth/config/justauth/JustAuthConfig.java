package com.chitchat.auth.config.justauth;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import me.zhyd.oauth.config.AuthConfig;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

import java.net.Proxy;
import java.util.Map;

/**
 * 第三方登录配置
 *
 * Created by Js on 2022/8/23 .
 **/
@Configuration
@ConfigurationProperties(prefix = "just.auth")
@Data
@RefreshScope
public class JustAuthConfig {

    private Map<String, AuthConfig> type;

    /**
     * http 相关的配置，可设置请求超时时间和代理配置
     */
    private JustAuthHttpConfig httpConfig;

    @Getter
    @Setter
    public static class JustAuthProxyConfig {
        private String type = Proxy.Type.HTTP.name();
        private String hostname;
        private int port;
    }

    @Getter
    @Setter
    public static class JustAuthHttpConfig {
        private int timeout;
        private Map<String, JustAuthProxyConfig> proxy;
    }

}

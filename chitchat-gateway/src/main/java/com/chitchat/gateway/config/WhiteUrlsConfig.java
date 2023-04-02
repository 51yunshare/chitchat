package com.chitchat.gateway.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 网关白名单配置
 *
 * Created by Js on 2022/7/29.
 */
@Data
@Component
@ConfigurationProperties(prefix="security.ignore")
@RefreshScope
public class WhiteUrlsConfig {
    private List<String> urls;
}

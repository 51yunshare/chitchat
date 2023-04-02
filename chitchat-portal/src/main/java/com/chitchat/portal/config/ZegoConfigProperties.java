package com.chitchat.portal.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

/**
 * 即构配置
 *
 * Created by Js on 2022/7/29 .
 **/
@Data
@Configuration
@ConfigurationProperties(prefix="chitchat.zego")
@RefreshScope
public class ZegoConfigProperties {

    /**
     * appId
     */
    private String appId;
    /**
     * 作为项目的鉴权密钥，在SDK中进行配置
     */
    private String appSign;
    /**
     * 用于后台服务请求接口的鉴权校验
     */
    private String serverSecret;

    /**
     * 用于后台服务回调接口的鉴权校验
     */
    private String callbackSecret;
    /**
     * 机构服务地址
     */
    private String serverUrl;

}

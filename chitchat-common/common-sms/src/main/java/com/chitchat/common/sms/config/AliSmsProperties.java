package com.chitchat.common.sms.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;

import java.util.HashMap;
import java.util.Map;

/**
 * 阿里云短信配置
 */
@Data
@ConfigurationProperties(prefix = "aliyun.sms")
@RefreshScope
public class AliSmsProperties {
    /**
     * AK
     */
    private String accessKeyId;
    private String accessKeySecret;
    /**
     * 域名地址
     * "dysmsapi.aliyuncs.com";//短信API产品域名（接口地址固定，无需修改）
     */
    private String domain;
    /**
     * 地区
     * cn-hangzhou
     */
    private String regionId;
    /**
     * 签名
     */
    private String signName;

    private Map<String, String> model = new HashMap<>();

}

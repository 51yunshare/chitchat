package com.chitchat.common.oss.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;

/**
 * Oss配置信息
 *
 * Created by Js on 2022/8/28.
 **/
@Data
@ConfigurationProperties(prefix = "aliyun.oss")
@RefreshScope
public class OssProperties {
    //访问站点-外网
    private String endpoint;
    //内网
    private String endpointInner;
    //accessKey
    private String accessKeyId;
    //accessKeySecret
    private String accessKeySecret;
    //存储桶
    private String bucketName;
}

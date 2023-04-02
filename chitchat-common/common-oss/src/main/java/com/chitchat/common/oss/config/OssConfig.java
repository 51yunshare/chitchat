package com.chitchat.common.oss.config;

import cn.hutool.core.util.StrUtil;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSClientBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

/**
 * oss配置
 *
 * Created by Js on 2022/8/28.
 */
@Configuration
@EnableConfigurationProperties(OssProperties.class)
@Slf4j
public class OssConfig {

    @Autowired
    private OssProperties ossProperties;

    /**
     * 获取存储桶
     * @return
     */
    public OssProperties getOssProperties(){
        return ossProperties;
    }

    /**
     * 实例化
     *
     * @return
     */
    @Bean
    @Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE )
    public OSSClient getOSSClient() {
        // 创建OSSClient实例。
        return (OSSClient) new OSSClientBuilder()
                .build(ossProperties.getEndpoint(), ossProperties.getAccessKeyId(), ossProperties.getAccessKeySecret());
    }

    public OSSClient getInnerOSSClient() {
        // 创建OSSClient实例。
        OSSClient ossClient = (OSSClient) new OSSClientBuilder()
                .build(StrUtil.isBlank(ossProperties.getEndpointInner()) ? ossProperties.getEndpoint() : ossProperties.getEndpointInner(),
                        ossProperties.getAccessKeyId(), ossProperties.getAccessKeySecret());
        return ossClient;
    }
}

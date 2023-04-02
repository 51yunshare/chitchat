package com.chitchat.common.sms.config;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * Created by Js on 2022/8/4.
 * 阿里短信通道配置
 */
@Getter
@Configuration
@EnableConfigurationProperties(AliSmsProperties.class)
@Slf4j
public class AliSmsConfig {
    @Autowired
    private AliSmsProperties aliSmsProperties;

    private static IAcsClient acsClient;

    @PostConstruct
    public void init(){
        log.info(">>> init ali short massage chanel");
        System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
        System.setProperty("sun.net.client.defaultReadTimeout", "10000");
        //初始化ascClient需要的几个参数
        final String product = "Dysmsapi";//短信API产品名称（短信产品名固定，无需修改）
        //初始化ascClient,暂时不支持多region（请勿修改）
        IClientProfile profile = DefaultProfile.getProfile(aliSmsProperties.getRegionId(), aliSmsProperties.getAccessKeyId(), aliSmsProperties.getAccessKeySecret());
        DefaultProfile.addEndpoint(aliSmsProperties.getRegionId(), product, aliSmsProperties.getDomain());
        this.acsClient = new DefaultAcsClient(profile);
    }

    public IAcsClient getIAcsClient(){
        return acsClient;
    }
}

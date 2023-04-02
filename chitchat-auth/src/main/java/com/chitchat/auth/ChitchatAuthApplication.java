package com.chitchat.auth;

import com.chitchat.admin.api.feign.FeignSysUserServiceI;
import com.chitchat.portal.api.feign.FeignPortalAccountServiceI;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * Created by Js on 2022/7/29.
 **/
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@EnableDiscoveryClient
@EnableFeignClients(basePackageClasses = {FeignPortalAccountServiceI.class, FeignSysUserServiceI.class})
public class ChitchatAuthApplication {
    public static void main(String[] args) {
        SpringApplication.run(ChitchatAuthApplication.class, args);
    }
}

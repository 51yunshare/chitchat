package com.chitchat.admin;

import com.chitchat.auth.api.feign.FeignAuthServiceI;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Created by Js on 2022/7/25 .
 **/
@SpringBootApplication
@EnableDiscoveryClient
@MapperScan(basePackages = "com.chitchat.admin.**.dao")
@EnableTransactionManagement
@EnableFeignClients(basePackageClasses = {FeignAuthServiceI.class})
public class ChitchatAdminApplication {
    public static void main(String[] args) {
        SpringApplication.run(ChitchatAdminApplication.class, args);
    }
}

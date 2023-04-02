package com.chitchat.user;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * Created by Js on 2022/7/25 .
 **/
@EnableDiscoveryClient
@SpringBootApplication
@MapperScan(basePackages = "com.chitchat.user.**.dao")
public class ChitchatUserApplication {

    public static void main(String[] args) {
        SpringApplication.run(ChitchatUserApplication.class, args);
    }
}

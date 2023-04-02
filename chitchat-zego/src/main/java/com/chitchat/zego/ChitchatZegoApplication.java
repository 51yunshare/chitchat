package com.chitchat.zego;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * Created by Js on 2022/7/25 .
 **/
@SpringBootApplication
@EnableDiscoveryClient
public class ChitchatZegoApplication {
    public static void main(String[] args) {
        SpringApplication.run(ChitchatZegoApplication.class, args);
    }
}

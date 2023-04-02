package com.chitchat.monitor;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * Created by Js on 2022/8/12 .
 **/
@EnableDiscoveryClient
@EnableAdminServer
@SpringBootApplication
public class ChitchatMonitorApplication {
    public static void main(String[] args) {
        SpringApplication.run(ChitchatMonitorApplication.class, args);
    }
}

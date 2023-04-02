package com.chitchat.portal;

import com.chitchat.admin.api.feign.FeignDictInfoServiceI;
import com.chitchat.admin.api.feign.FeignGoodsInfoServiceI;
import com.chitchat.admin.api.feign.FeignRoomTypeServiceI;
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
@MapperScan(basePackages = "com.chitchat.portal.**.dao")
@EnableTransactionManagement
@EnableFeignClients(basePackageClasses = {FeignAuthServiceI.class, FeignDictInfoServiceI.class,
        FeignGoodsInfoServiceI.class, FeignRoomTypeServiceI.class})
public class ChitchatPortalApplication {
    public static void main(String[] args) {
        SpringApplication.run(ChitchatPortalApplication.class, args);
    }
}

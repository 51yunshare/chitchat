package com.chitchat.oms;

import com.chitchat.admin.api.feign.FeignGoodsInfoServiceI;
import com.chitchat.portal.api.feign.FeignAccountBackpackServiceI;
import com.chitchat.portal.api.feign.FeignAccountBalanceServiceI;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * 订单服务
 *
 * Created by Js on 2022/9/13.
 **/
@SpringBootApplication
@EnableDiscoveryClient
@MapperScan(basePackages = "com.chitchat.oms.**.dao")
@EnableTransactionManagement
@EnableFeignClients(basePackageClasses = {FeignGoodsInfoServiceI.class, FeignAccountBalanceServiceI.class, FeignAccountBackpackServiceI.class})
public class ChitchatOmsApplication {
    public static void main(String[] args) {
        SpringApplication.run(ChitchatOmsApplication.class, args);
    }
}

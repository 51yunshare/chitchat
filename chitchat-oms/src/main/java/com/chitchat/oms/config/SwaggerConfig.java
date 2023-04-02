package com.chitchat.oms.config;

import com.chitchat.common.config.BaseSwaggerConfig;
import com.chitchat.common.config.SwaggerProperties;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Created by Js on 2022/7/28 .
 **/
@Configuration
@EnableSwagger2
public class SwaggerConfig extends BaseSwaggerConfig {

    @Override
    public SwaggerProperties swaggerProperties() {
        return SwaggerProperties.builder()
                .apiBasePackage("com.chitchat.oms.controller")
                .title("chitchat-oms 订单支付接口文档")
                .description("chitchat-闲聊接口文档Swagger版")
                .contactName("chitchat")
                .version("1.0")
                .enableSecurity(true)
                .contactName("闲聊科技有限公司")
                .build();
    }

    @Bean
    public BeanPostProcessor springfoxHandlerProviderBeanPostProcessor() {
        return generateBeanPostProcessor();
    }
}

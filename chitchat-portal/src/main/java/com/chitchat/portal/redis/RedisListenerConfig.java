package com.chitchat.portal.redis;

import com.chitchat.common.constant.NotifyConstant;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

/**
 * redis监听配置
 *
 * Created by Js 2022/9/8.
 */
@Configuration
public class RedisListenerConfig {
    /**
     * 消息监听适配器，注入接受消息方法，输入方法名字 反射方法
     *
     * 订阅者及其方法
     * @return
     */
    @Bean
    MessageListenerAdapter listenerAdapter(RedisMessageListener redisMessageListener) {
        //这个地方 是给messageListenerAdapter 传入一个消息接受的处理器，利用反射的方法调用“receiveMessage”
        //也有好几个重载方法，这边默认调用处理器的方法 叫handleMessage 可以自己到源码里面看
        return new MessageListenerAdapter(redisMessageListener, "onMessage");
    }

    /**
     * 创建消息监听容器
     *
     * @param redisConnectionFactory
     * @param messageListenerAdapter
     * @return
     */
    @Bean
    public RedisMessageListenerContainer getRedisMessageListenerContainer(RedisConnectionFactory redisConnectionFactory, MessageListenerAdapter messageListenerAdapter) {
        RedisMessageListenerContainer redisMessageListenerContainer = new RedisMessageListenerContainer();
        redisMessageListenerContainer.setConnectionFactory(redisConnectionFactory);
        //新增订阅者及订阅频道
        redisMessageListenerContainer.addMessageListener(messageListenerAdapter, new PatternTopic(NotifyConstant.REDIS_CHANNEL));
        return redisMessageListenerContainer;
    }
}

package com.chitchat.common.rabbitmq.config;

import com.chitchat.common.rabbitmq.enumerate.QueueEnum;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.CustomExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.RabbitListenerContainerFactory;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * 消息队列配置
 *
 * 为什么时候延迟插件？
 * 1.死信队列方式需要创建两个交换机（死信队列交换机+处理队列交换机）、两个队列（死信队列+处理队列）
 * 2.主要原因是每个消息的延迟时间不一样，所以只能使用插件，死信队列更适合统一的延迟时间的消息，(两个队列转移，先进先出，会造成出队列的消息可能没有过期，但是后面的过期不能消费)
 *
 * #设置为true后 生产者在消息入队列后，但是没有被路由到合适队列情况下，会被生产者者这边的ReturnCallback监听，不会自动删除，为false，会被自动剔除
 * template:
 *   mandatory: true
 * listener:
 *   simple:
 *     # 开启手动确认
 *     acknowledge-mode: manual
 *   #开启return 确认消息
 *   publisher-returns: true
 *   # ConfirmCallback开启发送到交换机Exchange触发回调
 *   publisher-confirm-type: correlated
 *
 */
@Configuration
public class RabbitMQConfig {

    /**
     * 消息序列化配置-对象传输可以配置
     *
     * 序列化Long类型会出问题
     */
    @Bean
    public RabbitListenerContainerFactory<?> rabbitListenerContainerFactory(ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(new Jackson2JsonMessageConverter());
        return factory;
    }

    /**
     * 背包过期-延迟插件消息队列所绑定的交换机
     */
    @Bean
    CustomExchange goodsDelayedDirect() {
        //创建一个自定义交换机，可以发送延迟消息
        Map<String, Object> args = new HashMap<>();
        //插件标记
        args.put("x-delayed-type", "direct");
        //交换机插件类型x-delayed-message
        return new CustomExchange(QueueEnum.QUEUE_GOODS_INVALID.getExchange(), "x-delayed-message",true, false,args);
    }

    /**
     * 背包过期-延迟插件队列
     */
    @Bean
    public Queue goodsDelayedQueue() {
        //true-持久化-默认值
//        new Queue(QueueEnum.QUEUE_GOODS_INVALID.getName(), true);
        return new Queue(QueueEnum.QUEUE_GOODS_INVALID.getName());
    }

    /**
     * 将背包过期-延迟插件队列绑定到交换机
     */
    @Bean
    public Binding goodsDelayedBinding(CustomExchange goodsDelayedDirect, Queue goodsDelayedQueue) {
        return BindingBuilder
                .bind(goodsDelayedQueue)
                .to(goodsDelayedDirect)
                .with(QueueEnum.QUEUE_GOODS_INVALID.getRouteKey())
                .noargs();
    }

}

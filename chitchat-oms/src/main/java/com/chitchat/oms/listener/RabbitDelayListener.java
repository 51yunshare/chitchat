package com.chitchat.oms.listener;

import com.chitchat.oms.service.AccountBalanceServiceI;
import com.rabbitmq.client.Channel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * RabbitMQ 延迟队列
 *
 * RabbitMQ 确认机制
 *
 * channel.basicReject(deliveryTag, true);
 * basic.reject方法拒绝deliveryTag对应的消息，第二个参数是否requeue，true则重新入队列，否则丢弃或者进入死信队列。该方法reject后，该消费者还是会消费到该条被reject的消息。
 *
 * channel.basicNack(deliveryTag, false, true);
 * basic.nack方法为不确认deliveryTag对应的消息，第二个参数是否应用于多消息，第三个参数是否requeue，与basic.reject区别就是同时支持多个消息，可以nack该消费者先前接收未ack的所有消息。nack后的消息也会被自己消费到。
 *
 * channel.basicRecover(true);
 * basic.recover是否恢复消息到队列，参数是是否requeue，true则重新入队列，并且尽可能的将之前recover的消息投递给其他消费者消费，而不是自己再次消费。false则消息会重新被投递给自己。
 *
 * Created by Js on 2022/9/29 .
 **/
@Component
@RequiredArgsConstructor
@Slf4j
public class RabbitDelayListener {

    private final AccountBalanceServiceI accountBalanceServiceI;

    /**
     * 背包道具失效
     *
     * @param backpackId
     * @param message
     * @param channel
     */
    @RabbitListener(queues = "chitchat.goods.invalid.queue")
    public void invalidProp(String backpackId, Message message, Channel channel) {
        log.info("======================= 道具失效，开始系统自动处理失效道具 =======================");
        try {
            if (accountBalanceServiceI.updateBackpackInvalidGoods(Long.valueOf(backpackId))) {
                log.info("======================= 处理成功 =======================");
            } else {
                log.info("======================= 处理失败，手动确认消息处理完毕=======================");
                // basicAck(tag,multiple)，multiple为true开启批量确认，小于tag值队列中未被消费的消息一次性确认
                // 消息的标识，false只确认当前一个消息收到，true确认所有consumer获得的消息（成功消费，消息从队列中删除 ）
                channel.basicAck(message.getMessageProperties().getDeliveryTag(), true);
            }
        } catch (IOException e) {
            log.info("=======================系统自动处理失效道具失败，重新入队=======================");
            try {
                channel.basicReject(message.getMessageProperties().getDeliveryTag(), true);
            } catch (Exception ioException) {
                log.error("系统处理失败");
            }
        }
    }
}

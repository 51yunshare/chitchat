package com.chitchat.oms.listener;

import com.chitchat.common.rabbitmq.enumerate.QueueEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 延迟服务
 *
 * 回调需要实现这两个方法
 * RabbitTemplate.ConfirmCallback 消息发送到交换机时，触发回调！
 * RabbitTemplate.ReturnsCallback 从交换机发送到队列，如果队列收到则会执行回调  这里有个问题，无论队列是否接收到，都会进入这个方法
 *
 * Created by Js on 2022/10/28 .
 **/
@Service
@Slf4j
public class RabbitDelayService {
    @Autowired
    private AmqpTemplate amqpTemplate;

    /**
     * 道具过期
     *
     * @param backpackId
     * @param delayTimes
     */
    public void sendMessage(String backpackId, final long delayTimes) {
        //给延迟队列发送消息
        amqpTemplate.convertAndSend(QueueEnum.QUEUE_GOODS_INVALID.getExchange(), QueueEnum.QUEUE_GOODS_INVALID.getRouteKey(), backpackId, new MessagePostProcessor() {
            @Override
            public Message postProcessMessage(Message message) throws AmqpException {
                //给消息设置延迟毫秒值
                //message.getMessageProperties().setDelay(i); 这个底层就是setHeader("x-delay",i);是一样的
                message.getMessageProperties().setHeader("x-delay", delayTimes);
                return message;
            }
        });
        log.info("send delay message 用户背包 backpackId:{}", backpackId);
    }
}

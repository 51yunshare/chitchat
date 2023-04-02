package com.chitchat.common.rabbitmq.enumerate;

import lombok.Getter;

/**
 * 消息队列枚举配置
 */
@Getter
public enum QueueEnum {
    /**
     * 消息通知队列
     */
    QUEUE_ORDER_CANCEL("chitchat.order.direct", "chitchat.order.cancel", "chitchat.order.cancel"),
    /**
     * 消息通知ttl队列
     */
    QUEUE_TTL_ORDER_CANCEL("chitchat.order.direct.ttl", "chitchat.order.cancel.ttl", "chitchat.order.cancel.ttl"),
    /**
     * 基于插件的订单延迟消息通知ttl队列
     */
    QUEUE_ORDER_PLUGIN_CANCEL("chitchat.order.direct.plugin.ttl", "chitchat.order.cancel.plugin", "chitchat.order.cancel.plugin"),
    /**
     * 基于插件的商品延迟消息通知ttl队列
     */
    QUEUE_GOODS_INVALID("chitchat.goods.invalid.exchange", "chitchat.goods.invalid.queue", "chitchat.goods.invalid.routing.key"),

    ;

    /**
     * 交换名称
     */
    private String exchange;
    /**
     * 队列名称
     */
    private String name;
    /**
     * 路由键
     */
    private String routeKey;

    QueueEnum(String exchange, String name, String routeKey) {
        this.exchange = exchange;
        this.name = name;
        this.routeKey = routeKey;
    }
}

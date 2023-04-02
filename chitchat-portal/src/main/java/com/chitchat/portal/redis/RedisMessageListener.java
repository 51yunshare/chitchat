package com.chitchat.portal.redis;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.chitchat.portal.service.zego.ZegoServiceI;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;

/**
 * 消息监听
 * <p>
 * Created by Js 2022/9/8.
 */
@Component
@Slf4j
public class RedisMessageListener implements MessageListener {
    @Autowired
    private ZegoServiceI zegoServiceI;

    @Override
    public void onMessage(Message message, byte[] bytes) {
        log.info("接收到消息，channel：【{}】，Topic：【{}】，消息内容：{}", message.getChannel(), new String(bytes), new String(message.getBody()));
        this.sendMsg(JSONObject.toJavaObject(JSON.parseObject(new String(message.getBody())), NotifyMessage.class));
    }

    public void sendMsg(NotifyMessage socketMsg) {
        //延迟三秒
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //发送消息-处理业务
        /*RoomSendCustomRequestDTO dto = new RoomSendCustomRequestDTO();
        dto.setFromUserId(socketMsg.getFromUserId());
        dto.setRoomId(socketMsg.getTargetId());
        dto.setMessageContent(socketMsg.getContent().toJSONString());
        dto.setToUserId(socketMsg.getToUserId());*/
        zegoServiceI.doSendCustomCommand(socketMsg.getFromUserId(), socketMsg.getTargetId(), socketMsg.getContent().toJSONString(), socketMsg.getToUserId());
    }
}

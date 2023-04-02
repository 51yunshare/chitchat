package com.chitchat.common.redis;

import com.chitchat.common.constant.RedisConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

@Component
public class RedisIdWorker {

    @Autowired
    private RedisTemplateUtil redisTemplateUtil;

    public Long nextId() {
        //获取当前时间
        LocalDateTime now = LocalDateTime.now();
        //获取当前时间得秒数
        long nowSecond = now.toEpochSecond(ZoneOffset.UTC);

        String format = now.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        // Redis Incrby 命令将 key 中储存的数字加上指定的增量值。
        // 如果 key 不存在，那么 key 的值会先被初始化为 0 ，然后再执行 INCRBY 命令。
        Long count = redisTemplateUtil.incr(RedisConstants.BUSINESS_NO_PREFIX + "ROOM_NUM:" + format);

        return nowSecond + count;
    }
}

package com.chitchat.common.redis;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.support.atomic.RedisAtomicInteger;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Redis-String 工具类
 *
 * Created by Js on 2022/7/23.
 */
@Component
public class RedisTemplateStringUtil {
    private StringRedisTemplate template;

    @Autowired
    public RedisTemplateStringUtil(StringRedisTemplate template) {
        this.template = template;
    }

    public StringRedisTemplate getStringRedisTemplate() {
        return this.template;
    }

    /**
     * 获取值
     *
     * @param key
     * @return
     */
    public String getKey(String key) {
        return template.opsForValue().get(key);
    }

    public <T> T getKey(String key, Class<T> tClass) {
        return JSONObject.parseObject(template.opsForValue().get(key), tClass);
    }

    public <T> List<T> getMultiKey(Set<String> keys, Class<T> tClass) {
        List<String> strings = template.opsForValue().multiGet(keys);
        if (CollectionUtils.isEmpty(strings)) {
            return new ArrayList<>();
        }
        List<T> result = new ArrayList<>();
        for (String string : strings) {
            result.add(JSONObject.parseObject(string, tClass));
        }
        return result;
    }

    public void setValue(String key, String value) {
        template.opsForValue().set(key, value);
    }

    public void setValue(String key, String value, long timeout, TimeUnit timeUnit) {
        template.opsForValue().set(key, value, timeout, timeUnit);
    }

    /**
     * 发送订阅消息
     *
     * @param channel
     * @param msg
     */
    public void send(String channel, Object msg) {
        template.convertAndSend(channel, msg);
    }

    public void remove(String key) {
        template.delete(key);
    }

    public void fuzzyRemove(String key) {
        template.delete(template.keys(key));
    }

    public void expire(String key, int hours) {
        template.expire(key, hours, TimeUnit.HOURS);
    }

    /**
     * 根据key获取计数器
     *
     * @param key key
     * @return
     */
    public int getRedisCounter(String key) {
        RedisAtomicInteger counter =
                new RedisAtomicInteger(key, template.getConnectionFactory());
        if (counter.get() == 0) {
            counter.expire(7, TimeUnit.DAYS);
        }
        return counter.incrementAndGet();
    }

    /**
     * 批量获取值
     *
     * @param key
     * @return
     */
    public Set<String> getKeys(String key) {
        return template.keys(key);
    }

}

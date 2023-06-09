package com.chitchat.common.redis;

import com.chitchat.common.constant.RedisConstants;
import com.chitchat.common.enumerate.EnumBusinessType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

@Component
@Slf4j
@RequiredArgsConstructor
public class BusinessNoGenerator {

    private final RedisTemplate redisTemplate;

    /**
     * @param businessType 业务类型枚举
     * @param digit        业务序号位数
     * @return
     */
    public String generate(EnumBusinessType businessType, Integer digit) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String date = LocalDateTime.now(ZoneOffset.of("+8")).format(formatter);
        String key = RedisConstants.BUSINESS_NO_PREFIX + businessType.getValue() + ":" + date;
        Long increment = redisTemplate.opsForValue().increment(key);
        return date + businessType.getValue() + String.format("%0" + digit + "d", increment);
    }

    public String generate(EnumBusinessType businessType) {
        return generate(businessType, businessType.getDigit());
    }

}

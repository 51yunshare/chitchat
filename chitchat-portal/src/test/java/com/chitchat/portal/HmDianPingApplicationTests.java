package com.chitchat.portal;

import cn.hutool.core.util.IdUtil;
import com.chitchat.common.redis.RedisTemplateUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SpringBootTest
class HmDianPingApplicationTests {

    private static final long BEGIN_TIMESTAMP = 1640995200L;

    private static int COUNT_BITS = 32;

    @Autowired
    private RedisTemplateUtil redisTemplateUtil;

    private ExecutorService es = Executors.newFixedThreadPool(500);

    public Long nextId(String keyPrefix) {
        //获取当前时间
        LocalDateTime now = LocalDateTime.now();
        //获取当前时间得秒数
        long nowSecond = now.toEpochSecond(ZoneOffset.UTC);
        long time = System.currentTimeMillis();

        String format = now.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        // Redis Incrby 命令将 key 中储存的数字加上指定的增量值。
        // 如果 key 不存在，那么 key 的值会先被初始化为 0 ，然后再执行 INCRBY 命令。
        Long count = redisTemplateUtil.incr("icr:" + keyPrefix + ":" + format);
        System.out.println("---"+time+",,,,"+count);
        System.out.println("....."+time/1000+",,,,"+count);
        System.out.println("+++++++++++" + nowSecond+",,,,"+count);
        return time << count;
    }

    @Test
    void testIdWorker() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(2);
        Runnable task = () -> {
            System.out.println(">>>>>>> start");
            for (int i = 0; i < 100; i++) {
                Long id = this.nextId("order");
                System.out.println("id = " + id);
            }
            latch.countDown();
        };
        long begin = System.currentTimeMillis();
        for (int i = 0; i < 2; i++) {
            es.submit(task);
        }
        latch.await();
        long end = System.currentTimeMillis();
        System.out.println("time = " + (end - begin));
    }

    public static void main(String[] args) {
        System.out.println(IdUtil.getSnowflake());
        System.out.println(IdUtil.getSnowflake());
        System.out.println(IdUtil.getSnowflake().nextId());
        System.out.println(IdUtil.getSnowflake().nextId());
        System.out.println(IdUtil.getSnowflakeNextId());
    }
}

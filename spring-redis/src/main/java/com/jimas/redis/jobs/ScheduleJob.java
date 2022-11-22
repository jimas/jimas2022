package com.jimas.redis.jobs;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @author liuqj
 */
@Component
@Slf4j
public class ScheduleJob {
    @Resource
    private RedisTemplate<String, String> redisTemplate;

    @Scheduled(fixedDelay = 10 * 1000)
    public void execute() {
        try {
            redisTemplate.opsForValue().set("test:k1", "hello", 1, TimeUnit.MINUTES);
            Thread.sleep(2000);
            log.info("redis OK:" + redisTemplate.opsForValue().get("test:k1"));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}

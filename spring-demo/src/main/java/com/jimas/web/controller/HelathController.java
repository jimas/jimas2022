package com.jimas.web.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author liuqj
 */
@RestController
@Slf4j(topic = "REQ_LOGGER")
public class HelathController {
    @Resource
    private RedisTemplate<String, String> redisTemplate;

    @RequestMapping("/health")
    public String health() {
        Long decrement = redisTemplate.opsForValue().decrement("KEY");
        log.info("health request num: {}", decrement);
        return "OK :" + decrement;
    }
}

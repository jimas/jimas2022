package com.jimas.web.controller;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author liuqj
 */
@RestController
public class HelathController {
    @Resource
    private RedisTemplate<String, String> redisTemplate;

    @RequestMapping("/health")
    public String health() {
        Long decrement = redisTemplate.opsForValue().decrement("KEY");
        return "OK :" + decrement;
    }
}

package com.jimas.redis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author liuqj
 */
@SpringBootApplication
@EnableScheduling
@EnableAsync
public class RedisApp {

    public static void main(String[] args) {
        SpringApplication.run(RedisApp.class, args);
    }
}

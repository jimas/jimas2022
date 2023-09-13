package com.jimas.rpc.dubbo.consumer;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author liuqj
 */
@SpringBootApplication
@EnableDubbo
public class DubboConsumerApp {
    public static void main(String[] args) {
        SpringApplication.run(DubboConsumerApp.class, args);
    }
}

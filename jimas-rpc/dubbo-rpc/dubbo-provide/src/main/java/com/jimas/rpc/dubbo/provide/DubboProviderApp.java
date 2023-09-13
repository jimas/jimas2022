package com.jimas.rpc.dubbo.provide;


import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author liuqj
 */
@SpringBootApplication
@EnableDubbo
public class DubboProviderApp {
    public static void main(String[] args) {
        SpringApplication.run(DubboProviderApp.class, args);
    }
}

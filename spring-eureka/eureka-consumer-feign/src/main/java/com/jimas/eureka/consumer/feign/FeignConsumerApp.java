package com.jimas.eureka.consumer.feign;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author liuqj
 */
@SpringBootApplication
@EnableFeignClients
public class FeignConsumerApp {

    public static void main(String[] args) {
        SpringApplication.run(FeignConsumerApp.class, args);
    }
}

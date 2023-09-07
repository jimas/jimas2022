package com.jimas.grpc.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author liuqj
 */
@SpringBootApplication
public class GrpcServerAppBoot {
    public static void main(String[] args) {
        SpringApplication.run(GrpcServerAppBoot.class, args);
    }
}

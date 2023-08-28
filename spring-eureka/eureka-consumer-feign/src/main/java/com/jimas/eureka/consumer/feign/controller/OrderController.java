package com.jimas.eureka.consumer.feign.controller;

import com.jimas.common.entity.OrderDTO;
import com.jimas.eureka.consumer.feign.service.OrderFeign;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * @author liuqj
 */
@RestController
@RequestMapping("order")
@Slf4j
public class OrderController {
    @Resource
    private OrderFeign orderService;

    @RequestMapping("findByNo")
    public OrderDTO getByOrderNo(@RequestParam String orderNo) {
        return orderService.findByNo(orderNo);
    }

    @RequestMapping("hystrix/findByNo")
    @HystrixCommand(fallbackMethod = "circuitFallback", commandProperties = {
            @HystrixProperty(name = "circuitBreaker.enabled", value = "true"),//是否开启断路器
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "10"),// 请求次数
            @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "10000"),// 时间窗口期
            @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "60")// 失败率达到多少后跳
    })
    public OrderDTO hystrixGetByOrderNo(@RequestParam String orderNo) {
        if (Objects.equals(orderNo, "hystrix")) {
            throw new RuntimeException("业务繁忙!");
        }
        return orderService.findByNo(orderNo);
    }

    public OrderDTO circuitFallback(String orderNo) {
        log.warn("=== circuitFallback ===");
        return new OrderDTO();

    }
}

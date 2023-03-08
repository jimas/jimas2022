package com.jimas.eureka.consumer.feign.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author liuqj
 */
@Slf4j
@Component
public class OrderFallbackFactory implements FallbackFactory<OrderFeign> {
    @Resource
    private OrderFeignFallback feignFallback;

    @Override
    public OrderFeign create(Throwable throwable) {
        if (throwable != null) {
            log.error("Fusing the drop {}", throwable.getMessage());
        }
        return feignFallback;
    }
}

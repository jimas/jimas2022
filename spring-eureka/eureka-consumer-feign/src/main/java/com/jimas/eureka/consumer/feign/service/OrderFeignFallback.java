package com.jimas.eureka.consumer.feign.service;

import com.jimas.common.entity.OrderDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author liuqj
 */
@Slf4j
@Component
public class OrderFeignFallback implements OrderFeign {
    @Override
    public OrderDTO findByNo(String orderNo) {
        log.warn("=====OrderFeignFallback=====");
        return new OrderDTO();
    }
}

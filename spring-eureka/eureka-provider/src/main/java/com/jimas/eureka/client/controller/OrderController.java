package com.jimas.eureka.client.controller;

import com.jimas.common.entity.OrderDTO;
import com.jimas.eureka.client.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author liuqj
 */
@RestController
@RequestMapping("order")
@Slf4j
public class OrderController {
    @Value("${server.port}")
    private String serverPort;
    @Resource
    private OrderService orderService;

    @RequestMapping("findByNo")
    public OrderDTO getByOrderNo(@RequestParam String orderNo) {
        log.debug("来自服务提供方:port{}", serverPort);
        try {
            //模拟业务响应
            Thread.sleep(RandomUtils.nextLong(100, 1200));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return orderService.findByNo(orderNo);
    }
}

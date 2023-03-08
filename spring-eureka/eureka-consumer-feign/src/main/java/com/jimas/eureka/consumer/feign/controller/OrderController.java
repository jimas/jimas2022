package com.jimas.eureka.consumer.feign.controller;

import com.jimas.common.entity.OrderDTO;
import com.jimas.eureka.consumer.feign.service.OrderFeign;
import lombok.extern.slf4j.Slf4j;
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
    @Resource
    private OrderFeign orderService;

    @RequestMapping("findByNo")
    public OrderDTO getByOrderNo(@RequestParam String orderNo) {
        return orderService.findByNo(orderNo);
    }
}

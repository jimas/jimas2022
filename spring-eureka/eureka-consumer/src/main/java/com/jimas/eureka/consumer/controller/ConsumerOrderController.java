package com.jimas.eureka.consumer.controller;

import com.alibaba.fastjson.JSON;
import com.jimas.common.entity.OrderDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

/**
 * @author liuqj
 */
@RestController
@Slf4j
public class ConsumerOrderController {
    @Resource
    private RestTemplate restTemplate;

    @RequestMapping(value = "/consumer", method = RequestMethod.GET)
    public OrderDTO consumer(@RequestParam String orderNo) {
        // 根据应用名称调用服务
        String json = restTemplate.getForObject("http://eureka-provider/order/findByNo?orderNo=" + orderNo, String.class);
        log.debug("remote result:{}", json);
        return JSON.parseObject(json, OrderDTO.class);
    }
}

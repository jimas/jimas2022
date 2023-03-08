package com.jimas.eureka.consumer.feign.service;

import com.jimas.common.entity.OrderDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * name:调用的服务名称 ，与服务提供者的 spring.application.name 一致
 * path:定义当前FeignClient的统一前缀
 *
 * @author liuqj
 */
@FeignClient(name = "eureka-provider", path = "order", fallbackFactory = OrderFallbackFactory.class)
public interface OrderFeign {
    /**
     * 获取订单信息
     *
     * @param orderNo
     * @return
     * @RequestParam("orderNo") 必须指定 value
     */
    @RequestMapping("/findByNo")
    OrderDTO findByNo(@RequestParam("orderNo") String orderNo);

}

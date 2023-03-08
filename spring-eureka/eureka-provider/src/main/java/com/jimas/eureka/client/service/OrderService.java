package com.jimas.eureka.client.service;


import com.jimas.common.entity.OrderDTO;

/**
 * @author liuqj
 */

public interface OrderService {
    OrderDTO findByNo(String orderNo);
}

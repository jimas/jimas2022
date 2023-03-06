package com.jimas.eureka.client.service;

import com.jimas.eureka.client.dto.OrderDTO;

/**
 * @author liuqj
 */

public interface OrderService {
    OrderDTO findByNo(String orderNo);
}

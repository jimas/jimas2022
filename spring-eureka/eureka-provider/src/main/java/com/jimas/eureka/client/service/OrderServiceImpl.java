package com.jimas.eureka.client.service;

import com.jimas.common.entity.OrderDTO;
import org.springframework.stereotype.Service;

/**
 * @author liuqj
 */
@Service
public class OrderServiceImpl implements OrderService {
    @Override
    public OrderDTO findByNo(String orderNo) {
        return OrderDTO.generateOrder(orderNo);
    }
}

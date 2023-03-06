package com.jimas.eureka.client.dto;

import lombok.Data;
import org.apache.commons.lang.math.RandomUtils;

/**
 * @author liuqj
 */
@Data
public class OrderDTO {
    private String orderNo;
    private String orderName;
    private String orderDesc;
    private Double orderPrice;
    private Integer orderCount;

    public static OrderDTO generateOrder(String orderNo) {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setOrderCount(RandomUtils.nextInt());
        orderDTO.setOrderDesc("电商大件订单");
        orderDTO.setOrderName("衣柜" + orderNo);
        orderDTO.setOrderPrice(RandomUtils.nextDouble());
        orderDTO.setOrderNo(orderNo);
        return orderDTO;

    }
}

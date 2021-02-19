package com.example.orderService.service;

import com.example.orderService.entity.Order;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 订单 服务类
 * </p>
 *
 * @author testjava
 * @since 2021-01-03
 */
public interface OrderService extends IService<Order> {

    String createOrder(String courseId, String memberId);

    Order getOrderInfoByOrderNo(String orderNo);
}

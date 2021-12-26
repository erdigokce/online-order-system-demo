package com.onlineordersystem.service;

import com.onlineordersystem.model.OrderCreateRequestDTO;
import com.onlineordersystem.model.OrderActionResultDTO;
import com.onlineordersystem.model.OrderListDTO;
import java.util.UUID;

public interface OrderService {

    OrderActionResultDTO createOrder(OrderCreateRequestDTO orderCreateRequestDTO);

    OrderActionResultDTO cancelOrder(UUID orderId);

    OrderActionResultDTO rejectOrder(UUID orderId);

    OrderActionResultDTO approveOrder(UUID orderId);

    OrderListDTO listUsersOrders(Integer page, Integer size);

    void deliverOrders();

    void generateReport(UUID sellerId);
}

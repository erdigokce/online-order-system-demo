package com.onlineordersystem.schduler;

import com.onlineordersystem.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class DeliveryScheduler {

    private final OrderService orderService;

    @Autowired
    public DeliveryScheduler(OrderService orderService) {
        this.orderService = orderService;
    }

    @Scheduled(fixedDelay = 1000 * 60L)
    public void deliverOrders() {
        // orderService.deliverOrders(0, 100);
    }
}

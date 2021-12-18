package com.onlineordersystem.controller.seller;

import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/seller/order")
public class OrderController {

    @PutMapping
    public void rejectOrder() {

    }

    @PutMapping
    public void approveOrder() {

    }

}

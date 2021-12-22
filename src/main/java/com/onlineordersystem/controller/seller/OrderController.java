package com.onlineordersystem.controller.seller;

import java.util.UUID;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/seller/order")
public class OrderController {

    @PutMapping("/reject/:orderId")
    public String rejectOrder(@PathVariable UUID orderId) {
        return orderId + " is rejected";
    }

    @PutMapping("/approve/:orderId")
    public String approveOrder(@PathVariable UUID orderId) {
        return orderId + " is approved";
    }

}

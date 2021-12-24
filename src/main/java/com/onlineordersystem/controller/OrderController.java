package com.onlineordersystem.controller;

import java.util.UUID;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order")
public class OrderController {

    @PreAuthorize("hasAuthority('SELLER')")
    @PutMapping("/:orderId/reject")
    public String rejectOrder(@PathVariable UUID orderId) {
        return orderId + " is rejected";
    }

    @PreAuthorize("hasAuthority('SELLER')")
    @PutMapping("/:orderId/approve")
    public String approveOrder(@PathVariable UUID orderId) {
        return orderId + " is approved";
    }

}

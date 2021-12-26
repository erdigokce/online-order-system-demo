package com.onlineordersystem.controller;

import com.onlineordersystem.model.OrderActionResultDTO;
import com.onlineordersystem.model.OrderCreateRequestDTO;
import com.onlineordersystem.model.OrderListDTO;
import com.onlineordersystem.service.OrderService;
import java.net.URI;
import java.util.UUID;
import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping("/order")
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }
    @PreAuthorize("hasAuthority('USER')")
    @GetMapping
    public ResponseEntity<OrderListDTO> listMyOrders(
        @RequestParam(required = false) @PositiveOrZero(message = "{common.error.pageIndex.mustBePositiveOrZero}") Integer pageIndex,
        @RequestParam(required = false) @Positive(message = "{common.error.pageSize.mustBePositive}") Integer pageSize
    ) {
        OrderListDTO result = orderService.listUsersOrders(pageIndex, pageSize);
        return ResponseEntity.ok(result);
    }

    @PreAuthorize("hasAuthority('USER')")
    @PostMapping
    public ResponseEntity<OrderActionResultDTO> createOrder(@Valid @RequestBody OrderCreateRequestDTO orderCreateRequestDTO) {
        OrderActionResultDTO orderCreateResult = orderService.createOrder(orderCreateRequestDTO);
        return ResponseEntity.created(URI.create("/order/" + orderCreateResult.getOrderId())).body(orderCreateResult);
    }

    @PreAuthorize("hasAuthority('USER')")
    @PutMapping("/{orderId}/cancel")
    public ResponseEntity<OrderActionResultDTO> cancelOrder(@PathVariable UUID orderId) {
        OrderActionResultDTO orderCancelResult = orderService.cancelOrder(orderId);
        return ResponseEntity.ok().body(orderCancelResult);
    }

    @PreAuthorize("hasAuthority('SELLER')")
    @PutMapping("/{orderId}/reject")
    public ResponseEntity<OrderActionResultDTO> rejectOrder(@PathVariable UUID orderId) {
        OrderActionResultDTO orderRejectResult = orderService.rejectOrder(orderId);
        return ResponseEntity.ok().body(orderRejectResult);
    }

    @PreAuthorize("hasAuthority('SELLER')")
    @PutMapping("/{orderId}/approve")
    public ResponseEntity<OrderActionResultDTO> approveOrder(@PathVariable UUID orderId) {
        OrderActionResultDTO orderApproveResult = orderService.approveOrder(orderId);
        return ResponseEntity.ok().body(orderApproveResult);

    }

}

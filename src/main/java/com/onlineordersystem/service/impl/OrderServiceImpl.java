package com.onlineordersystem.service.impl;

import com.onlineordersystem.OosRuntimeException;
import com.onlineordersystem.domain.Product;
import com.onlineordersystem.domain.User;
import com.onlineordersystem.domain.UserOrder;
import com.onlineordersystem.error.OrderError;
import com.onlineordersystem.model.OrderActionResultDTO;
import com.onlineordersystem.model.OrderCreateRequestDTO;
import com.onlineordersystem.model.OrderDTO;
import com.onlineordersystem.model.OrderListDTO;
import com.onlineordersystem.model.enumeration.OrderStatus;
import com.onlineordersystem.repository.OrderRepository;
import com.onlineordersystem.security.util.SessionUtil;
import com.onlineordersystem.service.OrderService;
import com.onlineordersystem.service.ProductService;
import com.onlineordersystem.service.UserService;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Consumer;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final UserService userService;
    private final ProductService productService;
    private final ModelMapper modelMapper;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, UserService userService, ProductService productService, ModelMapper modelMapper) {
        this.orderRepository = orderRepository;
        this.userService = userService;
        this.productService = productService;
        this.modelMapper = modelMapper;
    }

    @Transactional
    @Override
    public OrderActionResultDTO createOrder(OrderCreateRequestDTO orderCreateRequestDTO) {
        UserOrder newOrder = new UserOrder();
        Product product = productService.dropOutOfStock(orderCreateRequestDTO.getProductId(), orderCreateRequestDTO.getQuantity());
        newOrder.setProduct(product);
        newOrder.setQuantity(orderCreateRequestDTO.getQuantity());
        newOrder.setStatus(OrderStatus.CREATED);
        User user = userService.findUserByUsername(SessionUtil.getUsername()).orElseThrow(() -> new OosRuntimeException(OrderError.USER_NOT_FOUND));
        newOrder.setUser(user);

        UserOrder savedOrder = orderRepository.save(newOrder);

        return OrderActionResultDTO.builder().orderId(savedOrder.getId()).build();
    }

    @Transactional
    @Override
    public OrderActionResultDTO cancelOrder(UUID orderId) {
        Consumer<UserOrder> orderPreconditionConsumer = (UserOrder userOrder) -> {
            if (userOrder.getStatus().isCancellable()) {
                throw new OosRuntimeException(OrderError.CANNOT_CANCEL);
            }
        };
        Consumer<UserOrder> orderCallback = userOrder -> productService.returnToStock(userOrder.getProduct().getId(), userOrder.getQuantity());
        return updateOrderStatus(orderId, OrderStatus.CANCELLED, orderPreconditionConsumer, orderCallback);
    }

    @Transactional
    @Override
    public OrderActionResultDTO rejectOrder(UUID orderId) {
        Consumer<UserOrder> orderPreconditionConsumer = userOrder -> {
            if (!userOrder.getStatus().isAbleToReject()) {
                throw new OosRuntimeException(OrderError.CANNOT_REJECT);
            }
        };
        Consumer<UserOrder> orderCallback = userOrder -> productService.returnToStock(userOrder.getProduct().getId(), userOrder.getQuantity());
        return updateOrderStatus(orderId, OrderStatus.REJECTED, orderPreconditionConsumer, orderCallback);
    }

    @Transactional
    @Override
    public OrderActionResultDTO approveOrder(UUID orderId) {
        Consumer<UserOrder> orderPreconditionConsumer = userOrder -> {
            if (!userOrder.getStatus().isAcceptable()) {
                throw new OosRuntimeException(OrderError.CANNOT_ACCEPT);
            }
        };
        return updateOrderStatus(orderId, OrderStatus.ACCEPTED, orderPreconditionConsumer, null);
    }

    @Transactional(readOnly = true)
    @Override
    public OrderListDTO listUsersOrders(Integer page, Integer size) {
        User user = userService.findUserByUsername(SessionUtil.getUsername()).orElseThrow(() -> new OosRuntimeException(OrderError.USER_NOT_FOUND));

        Integer pageSize = Optional.ofNullable(size).orElse(10);
        Integer pageIndex = Optional.ofNullable(page).orElse(0);
        PageRequest pageRequest = PageRequest.of(pageIndex, pageSize);

        Page<UserOrder> foundOrders = orderRepository.findByUser(user, pageRequest);
        List<OrderDTO> orders = foundOrders.map(order -> modelMapper.map(order, OrderDTO.class)).toList();

        return OrderListDTO.builder()
            .orders(orders)
            .totalElements(foundOrders.getTotalElements())
            .totalPages(foundOrders.getTotalPages())
            .currentPage(foundOrders.getNumber())
            .pageSize(foundOrders.getSize())
            .build();
    }

    private OrderActionResultDTO updateOrderStatus(UUID orderId, OrderStatus orderStatus, Consumer<UserOrder> preConsumer, Consumer<UserOrder> postConsumer) {
        UserOrder userOrder = orderRepository.findById(orderId).orElseThrow(() -> new OosRuntimeException(OrderError.ORDER_NOT_FOUND));
        if (preConsumer != null) {
            preConsumer.accept(userOrder);
        }
        userOrder.setStatus(orderStatus);
        if (postConsumer != null) {
            postConsumer.accept(userOrder);
        }
        return OrderActionResultDTO.builder().orderId(userOrder.getId()).build();
    }
}

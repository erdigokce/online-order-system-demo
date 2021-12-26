package com.onlineordersystem.repository;

import com.onlineordersystem.domain.User;
import com.onlineordersystem.domain.UserOrder;
import com.onlineordersystem.model.enumeration.OrderStatus;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface OrderRepository extends PagingAndSortingRepository<UserOrder, UUID> {

    Page<UserOrder> findByUser(User user, Pageable pageable);

    List<UserOrder> findByStatus(OrderStatus status);

    List<UserOrder> findByStatusAndLastModifiedDateBetweenAndProductSellerId(OrderStatus status, LocalDateTime start, LocalDateTime end, UUID sellerId);
}

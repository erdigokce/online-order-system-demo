package com.onlineordersystem.repository;

import com.onlineordersystem.domain.User;
import com.onlineordersystem.domain.UserOrder;
import com.onlineordersystem.model.enumeration.OrderStatus;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface OrderRepository extends PagingAndSortingRepository<UserOrder, UUID> {

    Page<UserOrder> findByUser(User user, Pageable pageable);

    Page<UserOrder> findByStatusOrderByLastModifiedDate(OrderStatus accepted, Pageable pageable);
}

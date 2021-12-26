package com.onlineordersystem.repository;

import com.onlineordersystem.domain.User;
import com.onlineordersystem.domain.UserOrder;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface OrderRepository extends PagingAndSortingRepository<UserOrder, UUID> {

    Page<UserOrder> findByUser(User user, Pageable pageable);
}

package com.onlineordersystem.repository;

import com.onlineordersystem.domain.Seller;
import java.util.UUID;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SellerRepository extends CrudRepository<Seller, UUID> {

}
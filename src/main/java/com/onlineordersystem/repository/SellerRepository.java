package com.onlineordersystem.repository;

import com.onlineordersystem.domain.Seller;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SellerRepository extends CrudRepository<Seller, UUID> {

    boolean existsByBusinessName(String businessName);

    Optional<Seller> findByPrincipleEmail(String email);

    List<Seller> findByPrincipleEmailConfirmed(boolean isConfirmed);
}
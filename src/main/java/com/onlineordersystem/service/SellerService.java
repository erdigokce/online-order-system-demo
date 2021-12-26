package com.onlineordersystem.service;

import com.onlineordersystem.domain.Seller;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SellerService {

    UUID createSeller(Seller seller);

    Optional<Seller> findSeller(UUID sellerId);

    Optional<Seller> findSellerByEmail(String username);

    List<Seller> findAll();
}

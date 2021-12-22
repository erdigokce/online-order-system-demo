package com.onlineordersystem.service.impl;

import com.onlineordersystem.domain.Seller;
import com.onlineordersystem.repository.SellerRepository;
import com.onlineordersystem.service.SellerService;
import java.util.Optional;
import java.util.UUID;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SellerServiceImpl implements SellerService {

    private final SellerRepository sellerRepository;

    @Autowired
    public SellerServiceImpl(SellerRepository sellerRepository) {
        this.sellerRepository = sellerRepository;
    }

    @Transactional
    @Override
    public UUID createSeller(Seller seller) {
        Seller savedSeller = sellerRepository.save(seller);
        return savedSeller.getId();
    }

    @Transactional
    @Override
    public Optional<Seller> findSeller(UUID sellerId) {
        return sellerRepository.findById(sellerId);
    }
}

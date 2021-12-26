package com.onlineordersystem.service.impl;

import com.onlineordersystem.OosRuntimeException;
import com.onlineordersystem.domain.Seller;
import com.onlineordersystem.error.RegisterError;
import com.onlineordersystem.repository.SellerRepository;
import com.onlineordersystem.service.SellerService;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
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
    public UUID createSeller(final Seller seller) {
        if (sellerRepository.existsByBusinessName(seller.getBusinessName())) {
            throw new OosRuntimeException(RegisterError.BUSINESS_ALREADY_EXISTS);
        }
        Seller savedSeller = sellerRepository.save(seller);
        return savedSeller.getId();
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Seller> findSeller(final UUID sellerId) {
        return sellerRepository.findById(sellerId);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Seller> findSellerByEmail(String email) {
        return sellerRepository.findByPrincipleEmail(email);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Seller> findAll() {
        return sellerRepository.findByPrincipleEmailConfirmed(true);
    }
}

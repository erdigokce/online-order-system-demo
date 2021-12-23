package com.onlineordersystem.service.impl;

import com.onlineordersystem.OosRuntimeException;
import com.onlineordersystem.domain.Seller;
import com.onlineordersystem.domain.base.Principle;
import com.onlineordersystem.error.RegisterError;
import com.onlineordersystem.model.PrincipleDTO;
import com.onlineordersystem.model.RegisterResultDTO;
import com.onlineordersystem.model.RegisterSellerRequestDTO;
import com.onlineordersystem.security.Authority;
import com.onlineordersystem.service.PrincipleService;
import com.onlineordersystem.service.RegisterService;
import com.onlineordersystem.service.SellerService;
import java.util.UUID;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RegisterServiceImpl implements RegisterService {

    private final PasswordEncoder passwordEncoder;
    private final PrincipleService principleService;
    private final SellerService sellerService;
    private final ModelMapper mapper;

    @Autowired
    public RegisterServiceImpl(PrincipleService principleService, SellerService sellerService, PasswordEncoder passwordEncoder, ModelMapper mapper) {
        this.sellerService = sellerService;
        this.principleService = principleService;
        this.passwordEncoder = passwordEncoder;
        this.mapper = mapper;
    }

    @Transactional
    @Override
    public RegisterResultDTO registerSeller(final RegisterSellerRequestDTO registerRequestDTO) {
        if (principleService.userExists(registerRequestDTO.getEmail())) {
            throw new OosRuntimeException(RegisterError.EMAIL_ALREADY_EXISTS);
        }

        registerRequestDTO.setPassword(passwordEncoder.encode(registerRequestDTO.getPassword()));

        PrincipleDTO newPrinciple = mapper.map(registerRequestDTO, PrincipleDTO.class);
        newPrinciple.setAuthority(Authority.SELLER);
        newPrinciple.setEmailConfirmed(false);
        principleService.createUser(newPrinciple);

        Seller newSeller = mapper.map(registerRequestDTO, Seller.class);
        Principle principle = principleService.findEntityByEmail(newPrinciple.getEmail()).orElseThrow(() -> new OosRuntimeException(RegisterError.PRINCIPLE_NOT_FOUND));
        newSeller.setPrinciple(principle);
        UUID sellerId = sellerService.createSeller(newSeller);

        return new RegisterResultDTO(sellerId.toString());
    }

    @Transactional
    @Override
    public void confirmSellerEmail(final String confirmationKey) {
        Seller register = sellerService.findSeller(UUID.fromString(confirmationKey)).orElseThrow(() -> new OosRuntimeException(RegisterError.EMAIL_CONFIRMATION_FAILURE));
        register.getPrinciple().setEmailConfirmed(true);
    }
}

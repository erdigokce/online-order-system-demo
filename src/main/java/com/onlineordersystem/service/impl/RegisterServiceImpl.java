package com.onlineordersystem.service.impl;

import com.onlineordersystem.OosRuntimeException;
import com.onlineordersystem.domain.Seller;
import com.onlineordersystem.domain.base.Principle;
import com.onlineordersystem.error.RegisterError;
import com.onlineordersystem.model.PrincipleDTO;
import com.onlineordersystem.model.RegisterRequestDTO;
import com.onlineordersystem.model.RegisterResultDTO;
import com.onlineordersystem.security.roles.SellerRole;
import com.onlineordersystem.service.PrincipleService;
import com.onlineordersystem.service.RegisterService;
import com.onlineordersystem.service.SellerService;
import java.util.UUID;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RegisterServiceImpl implements RegisterService {

    private final PrincipleService principleService;
    private final SellerService sellerService;
    private final ModelMapper mapper = new ModelMapper();

    @Autowired
    public RegisterServiceImpl(PrincipleService principleService, SellerService sellerService) {
        this.sellerService = sellerService;
        this.principleService = principleService;
    }

    @Transactional
    @Override
    public RegisterResultDTO registerSeller(RegisterRequestDTO registerRequestDTO) {
        if (principleService.userExists(registerRequestDTO.getEmail())) {
            throw new OosRuntimeException(RegisterError.EMAIL_ALREADY_EXISTS);
        }
        PrincipleDTO newPrinciple = mapper.map(registerRequestDTO, PrincipleDTO.class);
        newPrinciple.setAuthority(new SellerRole());
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
    public void confirmSellerEmail(String confirmationKey) {
        Seller register = sellerService.findSeller(UUID.fromString(confirmationKey)).orElseThrow(() -> new OosRuntimeException(RegisterError.EMAIL_CONFIRMATION_FAILURE));
        register.getPrinciple().setEmailConfirmed(true);
    }
}

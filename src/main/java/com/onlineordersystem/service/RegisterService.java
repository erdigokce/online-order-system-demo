package com.onlineordersystem.service;

import com.onlineordersystem.model.RegisterResultDTO;
import com.onlineordersystem.model.RegisterSellerRequestDTO;

public interface RegisterService {

    RegisterResultDTO registerSeller(RegisterSellerRequestDTO registerRequestDTO);

    void confirmSellerEmail(String confirmationKey);
}

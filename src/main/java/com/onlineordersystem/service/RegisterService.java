package com.onlineordersystem.service;

import com.onlineordersystem.model.RegisterRequestDTO;
import com.onlineordersystem.model.RegisterResultDTO;

public interface RegisterService {

    RegisterResultDTO registerSeller(RegisterRequestDTO registerRequestDTO);

    void confirmSellerEmail(String confirmationKey);
}

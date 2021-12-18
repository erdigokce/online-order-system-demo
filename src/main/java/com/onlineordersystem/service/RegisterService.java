package com.onlineordersystem.service;

import com.onlineordersystem.model.RegisterConfirmationResultDTO;
import com.onlineordersystem.model.RegisterRequestDTO;
import com.onlineordersystem.model.RegisterResultDTO;
import org.springframework.security.provisioning.UserDetailsManager;

public interface RegisterService extends UserDetailsManager {

    RegisterResultDTO registerSeller(RegisterRequestDTO registerRequestDTO);

    RegisterConfirmationResultDTO confirmEmail(String confirmationKey);
}

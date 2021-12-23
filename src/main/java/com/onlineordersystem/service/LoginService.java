package com.onlineordersystem.service;

import com.onlineordersystem.model.LoginRequestDTO;
import com.onlineordersystem.model.LoginResultDTO;

public interface LoginService {

    LoginResultDTO authenticateUser(LoginRequestDTO loginRequestDTO);

    void logoutUser();
}

package com.onlineordersystem.service.impl;

import com.onlineordersystem.model.LoginRequestDTO;
import com.onlineordersystem.model.LoginResultDTO;
import com.onlineordersystem.model.PrincipleDTO;
import com.onlineordersystem.security.util.JwtUtils;
import com.onlineordersystem.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImpl implements LoginService {

    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;

    @Autowired
    public LoginServiceImpl(JwtUtils jwtUtils, AuthenticationManager authenticationManager) {
        this.jwtUtils = jwtUtils;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public LoginResultDTO authenticateUser(LoginRequestDTO loginRequestDTO) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginRequestDTO.getEmail(), loginRequestDTO.getPassword());
        Authentication authentication = authenticationManager.authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);
        PrincipleDTO userDetails = (PrincipleDTO) authentication.getPrincipal();
        return new LoginResultDTO(jwt, userDetails.getEmail(), userDetails.getAuthority());
    }

    @Override
    public void logoutUser() {
        SecurityContextHolder.getContext().setAuthentication(null);
    }
}

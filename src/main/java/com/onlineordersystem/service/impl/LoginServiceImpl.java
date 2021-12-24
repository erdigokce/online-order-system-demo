package com.onlineordersystem.service.impl;

import com.onlineordersystem.model.LoginRequestDTO;
import com.onlineordersystem.model.LoginResultDTO;
import com.onlineordersystem.model.PrincipleDTO;
import com.onlineordersystem.security.util.SessionUtil;
import com.onlineordersystem.service.LoginService;
import com.onlineordersystem.service.TokenService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Log4j2
@Service
public class LoginServiceImpl implements LoginService {

    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    @Autowired
    public LoginServiceImpl(TokenService tokenService, AuthenticationManager authenticationManager) {
        this.tokenService = tokenService;
        this.authenticationManager = authenticationManager;
    }

    @Transactional
    @Override
    public LoginResultDTO authenticateUser(LoginRequestDTO loginRequestDTO) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginRequestDTO.getEmail(), loginRequestDTO.getPassword());
        Authentication authentication = authenticationManager.authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = tokenService.generateJwtToken(authentication);
        tokenService.storeToken(jwt);
        PrincipleDTO userDetails = (PrincipleDTO) authentication.getPrincipal();
        return new LoginResultDTO(jwt, userDetails.getEmail(), userDetails.getAuthority());
    }

    @Transactional
    @Override
    public void logoutUser() {
        String username = SessionUtil.getUsername();
        try {
            tokenService.removeTokens(username);
        } catch (EmptyResultDataAccessException e) {
            log.error("No token found for user {}", username, e);
        }
        SecurityContextHolder.getContext().setAuthentication(null);
    }
}

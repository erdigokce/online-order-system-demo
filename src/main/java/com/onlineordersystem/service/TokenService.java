package com.onlineordersystem.service;

import org.springframework.security.core.Authentication;

public interface TokenService {

    void storeToken(String token);

    void removeTokens(String... subjects) ;

    String generateJwtToken(Authentication authentication);

    String getUserNameFromJwtToken(String token);

    boolean validateJwtToken(String authToken);

}

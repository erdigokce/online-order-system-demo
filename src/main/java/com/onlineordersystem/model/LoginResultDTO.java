package com.onlineordersystem.model;

import com.onlineordersystem.security.Authority;
import lombok.Getter;

@Getter
public class LoginResultDTO {

    private final String jwt;
    private static final String type = "Bearer";
    private final String email;
    private final Authority authority;

    public LoginResultDTO(String jwt, String email, Authority authority) {
        this.jwt = jwt;
        this.email = email;
        this.authority = authority;
    }
}

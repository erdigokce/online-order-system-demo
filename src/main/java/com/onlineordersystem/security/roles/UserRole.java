package com.onlineordersystem.security.roles;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

@Component
public class UserRole implements GrantedAuthority {

    @Override
    public String getAuthority() {
        return "USER";
    }
}

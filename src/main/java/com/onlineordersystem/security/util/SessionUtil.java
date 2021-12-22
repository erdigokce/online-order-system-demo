package com.onlineordersystem.security.util;

import com.onlineordersystem.model.PrincipleDTO;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public final class SessionUtil {

    private SessionUtil() {
    }

    public static String getUsername() {
        return getUserPrincipal().getUsername();
    }

    public static PrincipleDTO getUserPrincipal() {
        Object principal = getAuthentication().getPrincipal();
        if (principal instanceof String anonymousPrinciple) {
            PrincipleDTO principleDTO = new PrincipleDTO();
            principleDTO.setEmail(anonymousPrinciple);
            return principleDTO;
        }
        return (PrincipleDTO) principal;
    }

    private static Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }
}

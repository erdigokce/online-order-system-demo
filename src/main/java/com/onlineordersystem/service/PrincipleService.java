package com.onlineordersystem.service;

import com.onlineordersystem.domain.base.Principle;
import java.util.Optional;
import org.springframework.security.provisioning.UserDetailsManager;

public interface PrincipleService extends UserDetailsManager {

    Optional<Principle> findEntityByEmail(String email);
}

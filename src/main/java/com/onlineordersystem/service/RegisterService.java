package com.onlineordersystem.service;

import com.onlineordersystem.domain.base.PrincipleRelated;
import com.onlineordersystem.model.RegisterRequestDTO;
import com.onlineordersystem.model.RegisterResultDTO;
import com.onlineordersystem.security.Authority;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;

public interface RegisterService {

    <T extends PrincipleRelated> void confirmEmail(final String confirmationKey, final Function<UUID, Optional<T>> conformer);

    <R extends RegisterRequestDTO, T extends PrincipleRelated> RegisterResultDTO register(R registerRequestDTO, Function<T, UUID> creator, Authority authority);
}

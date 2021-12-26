package com.onlineordersystem.service.impl;

import com.onlineordersystem.OosRuntimeException;
import com.onlineordersystem.domain.base.Principle;
import com.onlineordersystem.domain.base.PrincipleRelated;
import com.onlineordersystem.error.RegisterError;
import com.onlineordersystem.model.PrincipleDTO;
import com.onlineordersystem.model.RegisterRequestDTO;
import com.onlineordersystem.model.RegisterResultDTO;
import com.onlineordersystem.security.Authority;
import com.onlineordersystem.service.PrincipleService;
import com.onlineordersystem.service.RegisterService;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RegisterServiceImpl implements RegisterService {

    private final PasswordEncoder passwordEncoder;
    private final PrincipleService principleService;
    private final ModelMapper mapper;

    @Autowired
    public RegisterServiceImpl(PrincipleService principleService, PasswordEncoder passwordEncoder, ModelMapper mapper) {
        this.principleService = principleService;
        this.passwordEncoder = passwordEncoder;
        this.mapper = mapper;
    }

    @Transactional
    @Override
    public <T extends PrincipleRelated> void confirmEmail(final String confirmationKey, final Function<UUID, Optional<T>> conformer) {
        try {
            UUID id = UUID.fromString(confirmationKey);
            T register = conformer.apply(id).orElseThrow(() -> new OosRuntimeException(RegisterError.EMAIL_CONFIRMATION_FAILURE));
            register.getPrinciple().setEmailConfirmed(true);
        } catch (IllegalArgumentException ex) {
            throw new OosRuntimeException(RegisterError.EMAIL_CONFIRMATION_FAILURE);
        }
    }

    @Override
    public <R extends RegisterRequestDTO, T extends PrincipleRelated> RegisterResultDTO register(R registerRequestDTO, Function<T, UUID> creator, Authority authority) {
        PrincipleDTO newPrinciple = registerPrinciple(registerRequestDTO, authority);
        T newAuthorizedEntity = mapper.map(registerRequestDTO, (Class<T>) authority.getAuthorityClass());
        Principle principle = principleService.findEntityByEmail(newPrinciple.getEmail()).orElseThrow(() -> new OosRuntimeException(RegisterError.PRINCIPLE_NOT_FOUND));
        newAuthorizedEntity.setPrinciple(principle);
        UUID entityId = creator.apply(newAuthorizedEntity);

        return new RegisterResultDTO(entityId.toString());
    }

    private PrincipleDTO registerPrinciple(RegisterRequestDTO registerRequestDTO, Authority authority) {
        if (principleService.userExists(registerRequestDTO.getEmail())) {
            throw new OosRuntimeException(RegisterError.EMAIL_ALREADY_EXISTS);
        }

        registerRequestDTO.setPassword(passwordEncoder.encode(registerRequestDTO.getPassword()));

        PrincipleDTO newPrinciple = mapper.map(registerRequestDTO, PrincipleDTO.class);
        newPrinciple.setAuthority(authority);
        newPrinciple.setEmailConfirmed(false);
        principleService.createUser(newPrinciple);
        return newPrinciple;
    }
}

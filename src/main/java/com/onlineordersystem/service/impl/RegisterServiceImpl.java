package com.onlineordersystem.service.impl;

import com.onlineordersystem.OosException;
import com.onlineordersystem.model.RegisterConfirmationResultDTO;
import com.onlineordersystem.domain.Sellers;
import com.onlineordersystem.error.RegisterError;
import com.onlineordersystem.model.RegisterRequestDTO;
import com.onlineordersystem.model.RegisterResultDTO;
import com.onlineordersystem.repository.RegisterRepository;
import com.onlineordersystem.service.RegisterService;
import java.util.UUID;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RegisterServiceImpl implements RegisterService {

    private final RegisterRepository repository;
    private final ModelMapper mapper = new ModelMapper();

    @Autowired
    public RegisterServiceImpl(RegisterRepository repository) {
        this.repository = repository;
    }

    @Transactional
    @Override
    public RegisterResultDTO registerSeller(RegisterRequestDTO registerRequestDTO) {
        Sellers register = new Sellers();
        mapper.map(registerRequestDTO, register);
        Sellers savedRegister = repository.save(register);
        RegisterResultDTO resultDTO = new RegisterResultDTO();
        UUID registerId = savedRegister.getId();
        if (registerId == null) {
            resultDTO.setError(RegisterError.UNEXPECTED_ERROR);
            return resultDTO;
        }
        resultDTO.setTicket(registerId.toString());
        return resultDTO;
    }

    @Transactional
    @Override
    public RegisterConfirmationResultDTO confirmEmail(String confirmationKey) {
        RegisterConfirmationResultDTO resultDTO = new RegisterConfirmationResultDTO();
        try {
            Sellers register = repository.findById(UUID.fromString(confirmationKey)).orElseThrow(() -> new OosException(RegisterError.EMAIL_CONFIRMATION_FAILURE));
            register.setEmailConfirmed(true);
            resultDTO.setSuccess(true);
        } catch (OosException e) {
            resultDTO.setError((RegisterError) e.getError());
            resultDTO.setSuccess(false);
            return resultDTO;
        }
        return resultDTO;
    }

    @Override
    public void createUser(UserDetails user) {

    }

    @Override
    public void updateUser(UserDetails user) {

    }

    @Override
    public void deleteUser(String username) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void changePassword(String oldPassword, String newPassword) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean userExists(String username) {
        return false;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }
}

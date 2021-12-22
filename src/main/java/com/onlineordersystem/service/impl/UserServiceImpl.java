package com.onlineordersystem.service.impl;

import com.onlineordersystem.repository.UserRepository;
import com.onlineordersystem.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository repository;
    private final ModelMapper mapper = new ModelMapper();

    @Autowired
    public UserServiceImpl(UserRepository repository) {
        this.repository = repository;
    }

}

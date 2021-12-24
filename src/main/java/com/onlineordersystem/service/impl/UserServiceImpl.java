package com.onlineordersystem.service.impl;

import com.onlineordersystem.domain.User;
import com.onlineordersystem.repository.UserRepository;
import com.onlineordersystem.service.UserService;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository repository;

    @Autowired
    public UserServiceImpl(UserRepository repository) {
        this.repository = repository;
    }

    @Transactional
    @Override
    public UUID createUser(User user) {
        User savedUser = repository.save(user);
        return savedUser.getId();
    }

    @Transactional
    @Override
    public Optional<User> findUser(UUID userId) {
        return repository.findById(userId);
    }
}

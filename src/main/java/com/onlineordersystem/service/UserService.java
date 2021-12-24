package com.onlineordersystem.service;

import com.onlineordersystem.domain.User;
import java.util.Optional;
import java.util.UUID;

public interface UserService {

    UUID createUser(User user);

    Optional<User> findUser(UUID sellerId);
}

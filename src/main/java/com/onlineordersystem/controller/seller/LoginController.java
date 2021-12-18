package com.onlineordersystem.controller.seller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/seller/login")
public final class LoginController {

    @Autowired
    public LoginController() {

    }

    @PostMapping
    public void login() {
    }

    @DeleteMapping
    public void logout() {

    }

}

package com.onlineordersystem.controller.seller;

import com.onlineordersystem.model.LoginRequestDTO;
import com.onlineordersystem.model.LoginResultDTO;
import com.onlineordersystem.service.LoginService;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/seller/login")
public final class LoginController {

    private final LoginService loginService;

    @Autowired
    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @PostMapping
    public ResponseEntity<LoginResultDTO> login(@Valid @RequestBody LoginRequestDTO loginRequestDTO) {
        LoginResultDTO loginResultDTO = loginService.authenticateUser(loginRequestDTO);
        return ResponseEntity.ok().body(loginResultDTO);
    }

    @DeleteMapping
    public void logout() {
        loginService.logoutUser();
    }

}

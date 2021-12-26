package com.onlineordersystem.controller;

import com.onlineordersystem.model.RegisterResultDTO;
import com.onlineordersystem.model.RegisterSellerRequestDTO;
import com.onlineordersystem.model.RegisterUserRequestDTO;
import com.onlineordersystem.security.Authority;
import com.onlineordersystem.service.RegisterService;
import com.onlineordersystem.service.SellerService;
import com.onlineordersystem.service.UserService;
import java.net.URI;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping(value = "/register")
public class RegisterController {

    private final RegisterService registerService;
    private final SellerService sellerService;
    private final UserService userService;

    @Autowired
    public RegisterController(RegisterService registerService, SellerService sellerService, UserService userService) {
        this.registerService = registerService;
        this.sellerService = sellerService;
        this.userService = userService;
    }

    @PostMapping("/seller")
    public ResponseEntity<RegisterResultDTO> registerSeller(@Valid @RequestBody RegisterSellerRequestDTO registerRequestDTO) {
        RegisterResultDTO registerResultDTO = registerService.register(registerRequestDTO, sellerService::createSeller, Authority.SELLER);
        return ResponseEntity.created(URI.create("/seller/" + registerResultDTO.getTicket())).body(registerResultDTO);
    }

    @PostMapping("/user")
    public ResponseEntity<RegisterResultDTO> registerUser(@Valid @RequestBody RegisterUserRequestDTO registerRequestDTO) {
        RegisterResultDTO registerResultDTO = registerService.register(registerRequestDTO, userService::createUser, Authority.USER);
        return ResponseEntity.created(URI.create("/user/" + registerResultDTO.getTicket())).body(registerResultDTO);
    }

    @PutMapping("/seller/confirmEmail")
    public ResponseEntity<Object> confirmSellerEmail(@Valid @NotBlank @RequestParam String confirmationKey) {
        registerService.confirmEmail(confirmationKey, sellerService::findSeller);
        return ResponseEntity.accepted().build();
    }
    @PutMapping("/user/confirmEmail")
    public ResponseEntity<Object> confirmUserEmail(@Valid @NotBlank @RequestParam String confirmationKey) {
        registerService.confirmEmail(confirmationKey, userService::findUser);
        return ResponseEntity.accepted().build();
    }

}

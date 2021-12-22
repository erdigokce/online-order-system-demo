package com.onlineordersystem.controller.seller;

import com.onlineordersystem.model.RegisterRequestDTO;
import com.onlineordersystem.model.RegisterResultDTO;
import com.onlineordersystem.service.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/seller/register")
public final class RegisterController {

    private final RegisterService registerService;

    @Autowired
    public RegisterController(RegisterService registerService) {
        this.registerService = registerService;
    }

    @PostMapping
    public ResponseEntity<RegisterResultDTO> registerSeller(@RequestBody RegisterRequestDTO registerRequestDTO) {
        RegisterResultDTO result = registerService.registerSeller(registerRequestDTO);
        return ResponseEntity.ok(result);
    }

    @PutMapping
    public ResponseEntity<Object> confirmEmail(@RequestParam String confirmationKey) {
        registerService.confirmSellerEmail(confirmationKey);
        return ResponseEntity.accepted().build();
    }

}

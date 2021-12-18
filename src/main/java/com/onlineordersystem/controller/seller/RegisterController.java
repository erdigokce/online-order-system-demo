package com.onlineordersystem.controller.seller;

import com.onlineordersystem.model.RegisterConfirmationResultDTO;
import com.onlineordersystem.model.RegisterRequestDTO;
import com.onlineordersystem.model.RegisterResultDTO;
import com.onlineordersystem.service.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController("/seller/register")
public final class RegisterController {

    private final RegisterService registerService;

    @Autowired
    public RegisterController(RegisterService registerService) {
        this.registerService = registerService;
    }

    @PostMapping
    public ResponseEntity<RegisterResultDTO> registerSeller(@RequestBody RegisterRequestDTO registerRequestDTO) {
        RegisterResultDTO result = registerService.registerSeller(registerRequestDTO);
        if (StringUtils.hasText(result.getTicket())) {
            return ResponseEntity.ok(result);
        }
        return ResponseEntity.badRequest().body(result);
    }

    @PutMapping
    public ResponseEntity<RegisterConfirmationResultDTO> confirmEmail(@RequestParam String confirmationKey) {
        RegisterConfirmationResultDTO result = registerService.confirmEmail(confirmationKey);
        if (Boolean.TRUE.equals(result.getSuccess())) {
            return ResponseEntity.accepted().body(result);
        }
        return ResponseEntity.badRequest().body(result);
    }

}

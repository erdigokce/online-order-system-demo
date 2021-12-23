package com.onlineordersystem.controller.seller;

import com.onlineordersystem.model.RegisterResultDTO;
import com.onlineordersystem.model.RegisterSellerRequestDTO;
import com.onlineordersystem.service.RegisterService;
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
@RequestMapping(value = "/seller/register")
public class RegisterController {

    private final RegisterService registerService;

    @Autowired
    public RegisterController(RegisterService registerService) {
        this.registerService = registerService;
    }

    @PostMapping
    public ResponseEntity<RegisterResultDTO> registerSeller(@Valid @RequestBody RegisterSellerRequestDTO registerRequestDTO) {
        RegisterResultDTO registerResultDTO = registerService.registerSeller(registerRequestDTO);
        return ResponseEntity.created(URI.create("/seller/" + registerResultDTO.getTicket())).body(registerResultDTO);
    }

    @PutMapping
    public ResponseEntity<Object> confirmEmail(@Valid @NotBlank @RequestParam String confirmationKey) {
        registerService.confirmSellerEmail(confirmationKey);
        return ResponseEntity.accepted().build();
    }

}

package com.onlineordersystem.controller.user;

import com.onlineordersystem.model.RegisterResultDTO;
import com.onlineordersystem.model.RegisterUserRequestDTO;
import com.onlineordersystem.security.Authority;
import com.onlineordersystem.service.RegisterService;
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
@RequestMapping(value = "/user/register")
public class UserRegisterController {

    private final RegisterService registerService;
    private final UserService userService;

    @Autowired
    public UserRegisterController(RegisterService registerService, UserService userService) {
        this.registerService = registerService;
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<RegisterResultDTO> registerSeller(@Valid @RequestBody RegisterUserRequestDTO registerRequestDTO) {
        RegisterResultDTO registerResultDTO = registerService.register(registerRequestDTO, userService::createUser, Authority.USER);
        return ResponseEntity.created(URI.create("/user/" + registerResultDTO.getTicket())).body(registerResultDTO);
    }

    @PutMapping
    public ResponseEntity<Object> confirmEmail(@Valid @NotBlank @RequestParam String confirmationKey) {
        registerService.confirmEmail(confirmationKey, userService::findUser);
        return ResponseEntity.accepted().build();
    }

}

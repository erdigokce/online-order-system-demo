package com.onlineordersystem.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequestDTO {

    @NotBlank(message = "register.error.email.required")
    @Size(min = 6, max = 255, message = "register.error.email.invalid")
    private String email;
    @NotBlank(message = "register.error.password.required")
    private String password;
}

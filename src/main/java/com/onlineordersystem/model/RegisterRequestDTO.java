package com.onlineordersystem.model;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterRequestDTO {

    @Email(regexp = "^[a-zA-Z0-9.!#$%&’*+/=?^_`\\{|\\}~-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$", message = "register.error.email.invalid")
    @NotBlank(message = "register.error.email.required")
    @Size(min = 6, max = 255, message = "register.error.email.invalid")
    private String email;
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,16}$", message = "register.error.password.malformed")
    @NotBlank(message = "register.error.password.required")
    private String password;
}

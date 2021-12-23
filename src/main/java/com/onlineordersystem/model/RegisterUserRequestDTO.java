package com.onlineordersystem.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterUserRequestDTO extends RegisterRequestDTO {

    @NotBlank(message = "{register.error.businessAddress.required}")
    @Size(max = 6000, message = "{register.error.businessAddress.tooLong}")
    private String address;
}

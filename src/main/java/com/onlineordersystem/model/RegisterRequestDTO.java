package com.onlineordersystem.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterRequestDTO {

    private String email;
    private String password;
    private String businessName;
    private String businessAddress;
    private boolean emailConfirmed;
}

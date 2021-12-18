package com.onlineordersystem.model;

import com.onlineordersystem.error.RegisterError;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterResultDTO {

    private RegisterError error;
    private String message;
    private String ticket;
}

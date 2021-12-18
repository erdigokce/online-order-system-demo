package com.onlineordersystem.model;

import com.onlineordersystem.error.RegisterError;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterConfirmationResultDTO {

    private RegisterError error;
    private Boolean success;
}

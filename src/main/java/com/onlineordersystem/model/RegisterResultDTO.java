package com.onlineordersystem.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterResultDTO {

    private String ticket;

    public RegisterResultDTO(String ticket) {
        this.ticket = ticket;
    }
}

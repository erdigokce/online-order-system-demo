package com.onlineordersystem;

import com.onlineordersystem.error.Error;
import lombok.Getter;

@Getter
public class OosRuntimeException extends RuntimeException {

    private final Error error;

    public OosRuntimeException(Error error) {
        super(error.getMessageKey());
        this.error = error;
    }

}

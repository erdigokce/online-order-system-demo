package com.onlineordersystem;

import com.onlineordersystem.error.Error;

public class OosException extends Exception {

    Error error;

    public OosException(Error error) {
        super(error.getKey());
        this.error = error;
    }

    public Error getError() {
        return error;
    }
}

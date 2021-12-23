package com.onlineordersystem.model;

import java.io.Serial;
import java.io.Serializable;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class ErrorResultDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 5304351676700992682L;

    private final String message;
    private final String code;
    private final Serializable payload;
    private final boolean displayable;

    private ErrorResultDTO(String message, String code, Serializable payload, boolean displayable) {
        this.message = message;
        this.code = code;
        this.payload = payload;
        this.displayable = displayable;
    }

    public static ErrorResultDTO withMessageAndErrorCode(String message, String errorCode) {
        return new ErrorResultDTO(message, errorCode, null, true);
    }

    public static ErrorResultDTO withMessageAndErrorCode(String message, String errorCode, Serializable payload, boolean displayable) {
        return new ErrorResultDTO(message, errorCode, payload, displayable);
    }
}

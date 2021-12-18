package com.onlineordersystem.error;

public enum RegisterError implements Error {
    INVALID_EMAIL((byte) 100),
    EMAIL_REQUIRED((byte) 101),
    EMAIL_CONFIRMATION_FAILURE((byte) 102),
    MALFORMED_PASSWORD((byte) 103),
    PASSWORD_REQUIRED((byte) 104),
    PASSWORD_TOO_SHORT((byte) 105),
    PASSWORD_TOO_LONG((byte) 106),
    BUSINESS_NAME_REQUIRED((byte) 107),
    BUSINESS_NAME_TOO_LONG((byte) 108),
    BUSINESS_ADDRESS_REQUIRED((byte) 109),
    BUSINESS_ADDRESS_TOO_LONG((byte) 110),
    UNEXPECTED_ERROR((byte) 111);

    private final byte code;

    RegisterError(byte code) {
        this.code = code;
    }

    @Override
    public byte getCode() {
        return code;
    }

    @Override
    public String getKey() {
        return this.name();
    }
}

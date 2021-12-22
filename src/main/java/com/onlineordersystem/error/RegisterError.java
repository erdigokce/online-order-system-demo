package com.onlineordersystem.error;

public enum RegisterError implements Error {
    INVALID_EMAIL("email.invalid"),
    EMAIL_REQUIRED("email.required"),
    EMAIL_ALREADY_EXISTS("email.exists"),
    EMAIL_CONFIRMATION_FAILURE("email.confirmationFailure"),
    MALFORMED_PASSWORD("password.malformed"),
    PASSWORD_REQUIRED("password.required"),
    PASSWORD_TOO_SHORT("password.tooShort"),
    PASSWORD_TOO_LONG("password.tooLong"),
    BUSINESS_NAME_REQUIRED("businessName.required"),
    BUSINESS_NAME_TOO_LONG("businessName.tooLong"),
    BUSINESS_ADDRESS_REQUIRED("businessAddress.required"),
    BUSINESS_ADDRESS_TOO_LONG("businessAddress.tooLong"),
    PRINCIPLE_NOT_FOUND("principle.notFound"),
    UNEXPECTED_ERROR("unexpectedError");

    private static final String MESSAGE_KEY_BASE = "register.error";
    private final String messageKey;

    RegisterError(String messageKey) {
        this.messageKey = messageKey;
    }

    @Override
    public String getMessageKey() {
        return String.format("%s.%s", MESSAGE_KEY_BASE, messageKey);
    }

}

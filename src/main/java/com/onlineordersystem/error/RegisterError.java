package com.onlineordersystem.error;

public enum RegisterError implements Error {
    EMAIL_ALREADY_EXISTS("email.exists"),
    EMAIL_CONFIRMATION_FAILURE("email.confirmationFailure"),
    PRINCIPLE_NOT_FOUND("principle.notFound"),
    BUSINESS_ALREADY_EXISTS("businessName.exists"),
    ROLE_NOT_FOUND("role.notFound");

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

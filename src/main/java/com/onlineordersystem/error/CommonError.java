package com.onlineordersystem.error;

public enum CommonError implements Error {
    UNEXPECTED_ERROR("unexpectedError"),
    UNAUTHORIZED_ACCESS("login.unauthorized");

    private static final String MESSAGE_KEY_BASE = "common.error";
    private final String messageKey;

    CommonError(String messageKey) {
        this.messageKey = messageKey;
    }

    @Override
    public String getMessageKey() {
        return String.format("%s.%s", MESSAGE_KEY_BASE, messageKey);
    }

}

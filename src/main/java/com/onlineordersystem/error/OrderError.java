package com.onlineordersystem.error;

public enum OrderError implements Error {
    USER_NOT_FOUND("user.notFound"),
    ORDER_NOT_FOUND("notFound"),
    CANNOT_CANCEL("notAllowedToCancel"),
    CANNOT_REJECT("notAllowedToReject"),
    CANNOT_ACCEPT("notAllowedToAccept");

    private static final String MESSAGE_KEY_BASE = "order.error";
    private final String messageKey;

    OrderError(String messageKey) {
        this.messageKey = messageKey;
    }

    @Override
    public String getMessageKey() {
        return String.format("%s.%s", MESSAGE_KEY_BASE, messageKey);
    }

}

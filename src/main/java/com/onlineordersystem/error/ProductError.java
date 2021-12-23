package com.onlineordersystem.error;

public enum ProductError implements Error {
    NOT_FOUND("notFound");

    private static final String MESSAGE_KEY_BASE = "product.error";
    private final String messageKey;

    ProductError(String messageKey) {
        this.messageKey = messageKey;
    }

    @Override
    public String getMessageKey() {
        return String.format("%s.%s", MESSAGE_KEY_BASE, messageKey);
    }
}

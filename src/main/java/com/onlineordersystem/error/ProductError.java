package com.onlineordersystem.error;

public enum ProductError implements Error {
    NOT_FOUND("notFound"),
    MUST_REQUEST_AT_LEAST_ONE_PRODUCT("mustRequestAtLeastOneProduct"),
    NO_FIELD_PRESENT("noFieldToUpdate"),
    NOT_ENOUGH_STOCK("notEnoughStock");

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

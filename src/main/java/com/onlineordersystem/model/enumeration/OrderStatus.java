package com.onlineordersystem.model.enumeration;

import lombok.Getter;

@Getter
public enum OrderStatus {
    CREATED(true, true, true),
    ACCEPTED(false, false, false),
    CANCELLED(false, false, false),
    REJECTED(false, false, false),
    DELIVERED(false, false, false);

    private final boolean isAcceptable;
    private final boolean isCancellable;
    private final boolean isAbleToReject;

    OrderStatus(boolean isAcceptable, boolean isCancellable, boolean isAbleToReject) {
        this.isAcceptable = isAcceptable;
        this.isCancellable = isCancellable;
        this.isAbleToReject = isAbleToReject;
    }
}

package com.onlineordersystem.service;

import org.springframework.context.MessageSourceResolvable;

public interface MessageService {

    /**
     * get message by code
     */
    String get(String code);

    String get(MessageSourceResolvable resolvable);

    /**
     * get message with parameters by code
     */
    String get(String code, Object... args);
}

package com.onlineordersystem.service.impl;

import com.onlineordersystem.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Service;

@Service
public class MessageServiceImpl implements MessageService {

    private final MessageSourceAccessor accessor;

    @Autowired
    public MessageServiceImpl(MessageSource messageSource) {
        this.accessor = new MessageSourceAccessor(messageSource, LocaleContextHolder.getLocale());
    }

    @Override
    public String get(String code) {
        return this.accessor.getMessage(code, LocaleContextHolder.getLocale());
    }

    @Override
    public String get(MessageSourceResolvable resolvable) {
        return this.accessor.getMessage(resolvable, LocaleContextHolder.getLocale());
    }

    @Override
    public String get(String code, Object... args) {
        return this.accessor.getMessage(code, args, LocaleContextHolder.getLocale());
    }
}
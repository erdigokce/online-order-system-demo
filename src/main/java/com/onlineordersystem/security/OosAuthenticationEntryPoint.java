package com.onlineordersystem.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.onlineordersystem.error.CommonError;
import com.onlineordersystem.error.ErrorUtils;
import com.onlineordersystem.model.ErrorResultDTO;
import com.onlineordersystem.service.MessageService;
import java.io.IOException;
import java.util.Collections;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

@Log4j2
@Component
public class OosAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final MessageService messageService;

    public OosAuthenticationEntryPoint(MessageService messageService) {
        this.messageService = messageService;
    }

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        log.error("Unauthorized error: {}", authException.getMessage());
        final String exceptionMessage = this.messageService.get(CommonError.UNAUTHORIZED_ACCESS.getMessageKey());
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        int unauthorized = HttpServletResponse.SC_UNAUTHORIZED;
        ErrorResultDTO errorResultDTO = ErrorResultDTO.withMessageAndErrorCode(exceptionMessage, String.valueOf(unauthorized));
        String body = new ObjectMapper().writeValueAsString(ErrorUtils.getResponseBody(Collections.singletonList(errorResultDTO)));
        response.setStatus(unauthorized);
        response.getWriter().write(body);
        response.getWriter().flush();
    }

}
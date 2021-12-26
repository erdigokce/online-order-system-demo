package com.onlineordersystem.error.handler;


import static com.onlineordersystem.error.ErrorUtils.getResponseBody;

import com.onlineordersystem.OosRuntimeException;
import com.onlineordersystem.error.CommonError;
import com.onlineordersystem.error.Error;
import com.onlineordersystem.model.ErrorResultDTO;
import com.onlineordersystem.service.MessageService;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@Log4j2
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    private final MessageService messageService;

    public RestExceptionHandler(MessageService messageService) {
        this.messageService = messageService;
    }

    @ExceptionHandler({OosRuntimeException.class})
    public ResponseEntity<Map<String, List<ErrorResultDTO>>> handleBusinessException(OosRuntimeException ex) {
        Optional<Error> optionalError = Optional.ofNullable(ex.getError());
        String errorMessage = this.messageService.get(optionalError.orElse(CommonError.UNEXPECTED_ERROR).getMessageKey());
        log.warn("Exception thrown and handled by advice. Exception detail: {}", errorMessage);
        HttpStatus httpStatus = optionalError.isEmpty() ? HttpStatus.INTERNAL_SERVER_ERROR : HttpStatus.BAD_REQUEST;
        ErrorResultDTO errorResultDTO = ErrorResultDTO.withMessageAndErrorCode(errorMessage, String.valueOf(httpStatus.value()));
        return new ResponseEntity<>(getResponseBody(Collections.singletonList(errorResultDTO)), new HttpHeaders(), httpStatus);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ErrorResultDTO errorResultDTO = ErrorResultDTO.withMessageAndErrorCode(ex.getMessage(), String.valueOf(status.value()));
        return super.handleExceptionInternal(ex, getResponseBody(Collections.singletonList(errorResultDTO)), headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        log.error(ex);
        ErrorResultDTO errorResultDTO = ErrorResultDTO.withMessageAndErrorCode(ex.getMessage(), HttpStatus.BAD_REQUEST.toString(), null, false);

        return new ResponseEntity<>(getResponseBody(Collections.singletonList(errorResultDTO)), new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        log.error(ex);
        List<ErrorResultDTO> errors = ex.getBindingResult()
            .getFieldErrors()
            .stream()
            .map(e -> this.messageService.get(e.getDefaultMessage()))
            .map(message -> ErrorResultDTO.withMessageAndErrorCode(message, String.valueOf(status.value())))
            .toList();

        return new ResponseEntity<>(getResponseBody(errors), headers, status);

    }

    @ExceptionHandler({ConstraintViolationException.class})
    public ResponseEntity<Map<String, List<ErrorResultDTO>>> handleConstraintViolationException(ConstraintViolationException ex) {

        List<ErrorResultDTO> errors = ex.getConstraintViolations()
            .stream()
            .map(ConstraintViolation::getMessage)
            .map(message -> ErrorResultDTO.withMessageAndErrorCode(message, String.valueOf(HttpStatus.BAD_REQUEST.value())))
            .toList();
        return new ResponseEntity<>(getResponseBody(errors), null, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({AccessDeniedException.class})
    public ResponseEntity<Map<String, List<ErrorResultDTO>>> handleGenericException(AccessDeniedException ex) {
        final String exceptionMessage = this.messageService.get(CommonError.UNAUTHORIZED_ACCESS.getMessageKey());
        log.error("Unauthorized access.", ex);
        HttpStatus unauthorized = HttpStatus.FORBIDDEN;
        ErrorResultDTO errorResultDTO = ErrorResultDTO.withMessageAndErrorCode(exceptionMessage, String.valueOf(unauthorized.value()));
        return new ResponseEntity<>(getResponseBody(Collections.singletonList(errorResultDTO)), new HttpHeaders(), unauthorized);
    }

}
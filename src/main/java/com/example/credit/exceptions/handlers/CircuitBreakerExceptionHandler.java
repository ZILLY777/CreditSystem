package com.example.credit.exceptions.handlers;

import com.example.credit.entity.response.ResponseBasicError;
import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class CircuitBreakerExceptionHandler {

    @ExceptionHandler({ CallNotPermittedException.class })
    public ResponseEntity<Object> handleCallNotPermitted(Exception ex, WebRequest request) {
        ResponseBasicError apiError = new ResponseBasicError(
                new ResponseBasicError.SubError(ex.getLocalizedMessage(), "Сервис временно недоступен"));
        return new ResponseEntity<>(
                apiError, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }
}

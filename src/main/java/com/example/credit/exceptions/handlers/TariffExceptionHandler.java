package com.example.credit.exceptions.handlers;

import com.example.credit.entity.response.Generic.ResponseBasicError;
import com.example.credit.entity.response.ResponseSubError;
import com.example.credit.exceptions.TariffNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class TariffExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({ TariffNotFoundException.class })
    public ResponseEntity<Object> handleUnknownTariff(Exception ex, WebRequest request) {
        ResponseBasicError<ResponseSubError> apiError = ResponseBasicError.wrap(
                new ResponseSubError(ex.getLocalizedMessage(), "Тариф не найден"));
        return new ResponseEntity<>(
                apiError, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }


}

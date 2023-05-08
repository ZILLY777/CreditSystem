package com.example.credit.exceptions.handlers;

import com.example.credit.entity.response.Generic.ResponseBasicError;
import com.example.credit.entity.response.ResponseSubError;
import com.example.credit.exceptions.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class LoanOrderExceptionHandler {

    @ExceptionHandler({ LoanAlreadyApproved.class })
    public ResponseEntity<Object> handleApprovedLoan(Exception ex, WebRequest request) {
        ResponseBasicError<ResponseSubError> apiError = ResponseBasicError.wrap(
                new ResponseSubError(ex.getLocalizedMessage(), "Кредит уже одобрен"));
        return new ResponseEntity<>(
                apiError, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({ LoanConsiderationException.class})
    public ResponseEntity<Object> handleConsideredLoan(Exception ex, WebRequest request) {
        ResponseBasicError<ResponseSubError> apiError = ResponseBasicError.wrap(
                new ResponseSubError(ex.getLocalizedMessage(), "Кредит на рассмотрении"));
        return new ResponseEntity<>(
                apiError, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({ TryLaterException.class})
    public ResponseEntity<Object> handleLaterLoan(Exception ex, WebRequest request) {
        ResponseBasicError<ResponseSubError> apiError = ResponseBasicError.wrap(
                new ResponseSubError(ex.getLocalizedMessage(), "Попробуйте позже"));
        return new ResponseEntity<>(
                apiError, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({ OrderNotFoundException.class})
    public ResponseEntity<Object> handleLoanOrderNotFound(Exception ex, WebRequest request) {
        ResponseBasicError<ResponseSubError> apiError = ResponseBasicError.wrap(
                new ResponseSubError(ex.getLocalizedMessage(), "Заявка не найдена"));
        return new ResponseEntity<>(
                apiError, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({ OrderImpossibleToDelete.class})
    public ResponseEntity<Object> handleLoanOrderImpossibleToDelete(Exception ex, WebRequest request) {
        ResponseBasicError<ResponseSubError> apiError = ResponseBasicError.wrap(
                new ResponseSubError(ex.getLocalizedMessage(), "Невозможно удалить заявку"));
        return new ResponseEntity<>(
                apiError, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }
}

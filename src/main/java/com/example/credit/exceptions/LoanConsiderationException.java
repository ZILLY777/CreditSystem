package com.example.credit.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class LoanConsiderationException extends RuntimeException {
    public LoanConsiderationException(){super("LOAN_CONSIDERATION");}
}

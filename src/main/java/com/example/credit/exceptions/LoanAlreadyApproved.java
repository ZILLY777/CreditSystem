package com.example.credit.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class LoanAlreadyApproved extends RuntimeException{
    public LoanAlreadyApproved(){super("LOAN_ALREADY_APPROVED");}
}

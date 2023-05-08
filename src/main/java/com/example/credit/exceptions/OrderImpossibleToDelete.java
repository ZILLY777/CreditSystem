package com.example.credit.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class OrderImpossibleToDelete extends RuntimeException{
    public OrderImpossibleToDelete(){super("ORDER_IMPOSSIBLE_TO_DELETE");}
}

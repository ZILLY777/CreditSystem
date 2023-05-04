package com.example.credit.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class OrderNotFoundException extends Exception{
    public OrderNotFoundException(){super("ORDER_NOT_FOUND");}
}

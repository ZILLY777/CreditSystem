package com.example.credit.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class TariffNotFoundException extends RuntimeException{
    public TariffNotFoundException(){ super("TARIFF_NOT_FOUND");}
}

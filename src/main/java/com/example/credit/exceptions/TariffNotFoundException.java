package com.example.credit.exceptions;

import com.example.credit.entity.response.ResponseData;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class TariffNotFoundException extends Exception {
    public TariffNotFoundException(){ super("TARIFF_NOT_FOUND");}
}

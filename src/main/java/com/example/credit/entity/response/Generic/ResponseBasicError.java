package com.example.credit.entity.response.Generic;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ResponseBasicError<T>{

    private T error;

    public ResponseBasicError(T error){this.error = error;}

    public static <T>ResponseBasicError<T> wrap(T error){return new ResponseBasicError<>(error);}

}

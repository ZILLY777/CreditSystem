package com.example.credit.entity.response;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ResponseSubError {
    private Object code;
    private String message;
}

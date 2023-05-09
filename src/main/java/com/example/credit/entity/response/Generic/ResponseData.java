package com.example.credit.entity.response.Generic;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ResponseData<T> {
    private T data;

    public ResponseData(T data) {
        this.data = data;
    }

    public static <T> ResponseData<T> wrap(T data) {
        return new ResponseData<>(data);
    }

}

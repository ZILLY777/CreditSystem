package com.example.credit.entity.response;

import com.example.credit.constants.OrderErorEnum;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ResponseBasicError{

    private SubError error;

    public ResponseBasicError(SubError error){
        super();
        this.error = error;
    }

    @Getter
    public static class SubError{
        private Object code;
        private String message;

        public SubError(OrderErorEnum code, String message){
            this.code = code;
            this.message= message;
        }
        public SubError(String code, String message){
            this.code = code;
            this.message= message;
        }
    }
}

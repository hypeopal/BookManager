package com.example.bookmanager.Exception;

import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException{
    private final int errCode;
    private final int httpStatusCode;
    public BusinessException(int errCode, int httpStatusCode, String message) {
        super(message);
        this.errCode = errCode;
        this.httpStatusCode = httpStatusCode;
    }
}

package com.example.bookmanager.Exception;

import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException{
    private final int errCode;
    public BusinessException(int errCode, String message) {
        super(message);
        this.errCode = errCode;
    }
}

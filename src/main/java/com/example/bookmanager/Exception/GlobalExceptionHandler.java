package com.example.bookmanager.Exception;

import com.example.bookmanager.Utils.Result;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<Result<String>> handleBookInfoMismatch(BusinessException e) {
        return new ResponseEntity<>(Result.error(e.getErrCode(), e.getMessage()), HttpStatus.BAD_REQUEST);
    }
}

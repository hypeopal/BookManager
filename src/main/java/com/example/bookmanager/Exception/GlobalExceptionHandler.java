package com.example.bookmanager.Exception;

import com.example.bookmanager.Utils.Result;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<Result> handleBusinessException(BusinessException e) {
        return new ResponseEntity<>(Result.error(e.getErrCode(), e.getMessage()), HttpStatusCode.valueOf(e.getHttpStatusCode()));
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<Result> handleMissingServletRequestParameterException(MissingServletRequestParameterException e) {
        String parameterName = e.getParameterName();
        return new ResponseEntity<>(Result.error(3, "Missing parameter: " + parameterName), HttpStatus.BAD_REQUEST);
    }
}

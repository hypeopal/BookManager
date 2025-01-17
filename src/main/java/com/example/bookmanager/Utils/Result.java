package com.example.bookmanager.Utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result<T> {
    private int code;
    private String message;
    private T data;

    public static <T> Result<T> success(T data) {
        return new Result<>(0, "success", data);
    }
    public static <T> Result<T> success(T data, String message) {
        return new Result<>(0, message, data);
    }
    public static <T> Result<T> error(int code, String message) {
        return new Result<>(code, message, null);
    }
}

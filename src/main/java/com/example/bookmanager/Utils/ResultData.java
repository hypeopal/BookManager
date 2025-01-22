package com.example.bookmanager.Utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResultData<T> extends Result {
    private T data;

    public ResultData(int i, String message, T data) {
        super(i, message);
        this.data = data;
    }

    public static <T> ResultData<T> error(int code, String message, T data) {
        return new ResultData<>(code, message, data);
    }

    public static <T> ResultData<T> success(String message, T data) {
        return new ResultData<>(0, message, data);
    }
}

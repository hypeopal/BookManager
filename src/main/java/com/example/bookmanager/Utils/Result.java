package com.example.bookmanager.Utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result {
    private int code;
    private String message;

    public static Result success() {
        return new Result(0, "success");
    }
    public static Result success(String message) {
        return new Result(0, message);
    }
    public static Result error(int code, String message) {
        return new Result(code, message);
    }
}

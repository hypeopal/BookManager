package com.example.bookmanager.Utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserClaims {
    private String username;
    private boolean status;
    private boolean admin;

    Map<String, Object> toMap() {
        return Map.of("userName", username, "status", status, "admin", admin);
    }
}

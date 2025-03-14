package com.example.bookmanager.Utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserClaims {
    private Long id;
    private String username;

    Map<String, Object> toMap() {
        return Map.of("id", id, "userName", username);
    }
}

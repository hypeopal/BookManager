package com.example.bookmanager.Entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class User {
    private Long id;
    private String username;
    private String password;
    private LocalDateTime createdAt;
    private LocalDateTime lastUpdate;
    private boolean status;
    private boolean admin;
}

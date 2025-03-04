package com.example.bookmanager.DTO;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserDTO {
    private Long id;
    private String username;
    private LocalDateTime createdAt;
    private LocalDateTime lastUpdate;
    private boolean status;
    private boolean admin;
}

package com.example.bookmanager.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ChatRequest {
    @NotNull
    @NotBlank
    private String model;
    @NotNull
    @NotBlank
    private String prompt;
}

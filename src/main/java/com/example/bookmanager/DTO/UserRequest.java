package com.example.bookmanager.DTO;

import com.example.bookmanager.Annotation.NoSpace;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserRequest {
    @NotBlank(message = "Username is required")
    @NoSpace(message = "Username cannot contain spaces")
    @Size(min = 5, max = 15, message = "Username must be between 5 and 15 characters")
    @Pattern(regexp = "^[a-zA-Z0-9_]+$", message = "Username can only contain alphanumeric characters and underscores")
    private String username;

    @NotBlank(message = "Password is required")
    @NoSpace(message = "Password cannot contain spaces")
    @Size(min = 6, max = 20, message = "Password must be between 6 and 20 characters")
    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*[0-9]).{6,}$",
            message = "Password must contain at least one letter and one number")
    private String password;
}

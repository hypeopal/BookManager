package com.example.bookmanager.DTO;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@EqualsAndHashCode(callSuper = true)
@Data
@ToString(callSuper = true)
public class ChangePassword extends UserRequest {
    private String newPassword;

    public ChangePassword(String username, String oldPassword, String newPassword) {
        super(username, oldPassword);
        this.newPassword = newPassword;
    }
}

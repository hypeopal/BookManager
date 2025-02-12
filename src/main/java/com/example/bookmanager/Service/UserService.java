package com.example.bookmanager.Service;

import com.example.bookmanager.DTO.UserDTO;
import com.example.bookmanager.Utils.UserClaims;

import java.util.List;

public interface UserService {
    boolean isUsernameExists(String username);
    void signup(String username, String password);
    UserClaims validateLogin(String username, String password);
    void deleteUser(String username);
    void setAdmin(Long id);
    void unsetAdmin(Long id);
    void banUser(Long id);
    void unbanUser(Long id);
    List<UserDTO> getUserList();
}

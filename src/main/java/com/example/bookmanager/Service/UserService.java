package com.example.bookmanager.Service;

import com.example.bookmanager.DTO.ChangePassword;
import com.example.bookmanager.DTO.UserDTO;

import java.util.List;
import java.util.Map;

public interface UserService {
    boolean isUsernameExists(String username);
    void signup(String username, String password);
    void deleteUser(String username);
    void setAdmin(Long id);
    void unsetAdmin(Long id);
    void banUser(Long id);
    void unbanUser(Long id);
    List<UserDTO> getUserList();
    Map<String, String> login(String username, String password);
    void changePassword(ChangePassword userRequest);
}

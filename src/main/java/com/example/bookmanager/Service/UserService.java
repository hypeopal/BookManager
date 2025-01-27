package com.example.bookmanager.Service;

import com.example.bookmanager.Utils.UserClaims;

public interface UserService {
    boolean isUsernameExists(String username);
    void signup(String username, String password);
    UserClaims validateLogin(String username, String password);
    void deleteUser(String username);
}

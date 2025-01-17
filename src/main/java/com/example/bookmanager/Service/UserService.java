package com.example.bookmanager.Service;

public interface UserService {
    boolean isUsernameExists(String username);
    void signup(String username, String password);
    boolean validateLogin(String username, String password);
}

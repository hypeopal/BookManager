package com.example.bookmanager.Service.impl;

import com.example.bookmanager.Entity.User;
import com.example.bookmanager.Mapper.UserMapper;
import com.example.bookmanager.Service.UserService;
import com.example.bookmanager.Utils.BCryptUtil;
import com.example.bookmanager.Utils.UserClaims;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;

    public UserServiceImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public boolean isUsernameExists(String username) {
        return userMapper.findByUsername(username) > 0;
    }

    @Override
    public void signup(String username, String password) {
        String hashedPassword = BCryptUtil.hashPassword(password);
        userMapper.insertUser(username, hashedPassword);
    }

    @Override
    public UserClaims validateLogin(String username, String password) {
        User user = userMapper.getUserByUsername(username);
        String passwordHash = userMapper.getPasswordHashByUsername(username);
        if (!BCryptUtil.checkPassword(password, passwordHash)) return null;
        return new UserClaims(username, user.isStatus(), user.isAdmin());
    }

    @Override
    public void deleteUser(String username) {
        userMapper.deleteUser(username);
    }
}

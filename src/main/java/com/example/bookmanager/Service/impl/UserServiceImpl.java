package com.example.bookmanager.Service.impl;

import com.example.bookmanager.Mapper.UserMapper;
import com.example.bookmanager.Service.UserService;
import com.example.bookmanager.Utils.BCryptUtil;
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
    public boolean validateLogin(String username, String password) {
        String passwordHash = userMapper.getPasswordHashByUsername(username);
        return BCryptUtil.checkPassword(password, passwordHash);
    }
}

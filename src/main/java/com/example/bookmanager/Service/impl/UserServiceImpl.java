package com.example.bookmanager.Service.impl;

import com.example.bookmanager.Annotation.LogRecord;
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

    @LogRecord
    @Override
    public UserClaims validateLogin(String username, String password) {
        User user = userMapper.getUserByUsername(username);
        String passwordHash = userMapper.getPasswordHashByUsername(username);
        if (!BCryptUtil.checkPassword(password, passwordHash)) return null;
        return new UserClaims(user.getId(), username, user.isStatus(), user.isAdmin());
    }

    @LogRecord
    @Override
    public void deleteUser(String username) {
        userMapper.deleteUser(username);
    }

    @LogRecord
    @Override
    public void setAdmin(Long id) {
        userMapper.setAdmin(id);
    }

    @LogRecord
    @Override
    public void unsetAdmin(Long id) {
        userMapper.unsetAdmin(id);
    }

    @LogRecord
    @Override
    public void banUser(Long id) {
        userMapper.banUser(id);
    }

    @LogRecord
    @Override
    public void unbanUser(Long id) {
        userMapper.unbanUser(id);
    }
}

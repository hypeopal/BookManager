package com.example.bookmanager.Service.impl;

import com.example.bookmanager.Annotation.LogRecord;
import com.example.bookmanager.Config.LibraryConfig;
import com.example.bookmanager.DTO.ChangePassword;
import com.example.bookmanager.DTO.UserDTO;
import com.example.bookmanager.Entity.User;
import com.example.bookmanager.Exception.BusinessException;
import com.example.bookmanager.Mapper.UserMapper;
import com.example.bookmanager.Service.RedisService;
import com.example.bookmanager.Service.UserService;
import com.example.bookmanager.Utils.BCryptUtil;
import com.example.bookmanager.Utils.JWTUtil;
import com.example.bookmanager.Utils.UserClaims;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;
    private final RedisService redisService;
    private final LibraryConfig libraryConfig;

    public UserServiceImpl(UserMapper userMapper, RedisService redisService, LibraryConfig libraryConfig) {
        this.userMapper = userMapper;
        this.redisService = redisService;
        this.libraryConfig = libraryConfig;
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
    public Map<String, String> login(String username, String password) {
        if (isUsernameExists(username)) {
            User user = userMapper.getUserByUsername(username);
            String passwordHash = userMapper.getPasswordHashByUsername(username);
            if (BCryptUtil.checkPassword(password, passwordHash)) {
                redisService.setAdminStatus(user.getId(), user.isAdmin());
                redisService.setUserStatus(user.getId(), user.isStatus());
                UserClaims claims = new UserClaims(user.getId(), username);

                String token = JWTUtil.generateToken(claims);
                Map<String, String> map = new java.util.HashMap<>(Map.of("token", token));
                if (!user.isStatus()) {
                    map.put("warning", "Your account has been banned");
                }
                return map;
            }
        }
        throw new BusinessException(2, 400, "Invalid username or password");
    }

    @LogRecord
    @Override
    @Transactional
    public void changePassword(ChangePassword userRequest) {
        User user = userMapper.getUserByUsername(userRequest.getUsername());
        if (user == null) throw new BusinessException(2, 400, "User not found");
        if (ChronoUnit.DAYS.between(user.getLastUpdate(), LocalDateTime.now()) < libraryConfig.getChangePassDuration())
            throw new BusinessException(2, 400, "Change password too frequently");
        String passwordHash = user.getPassword();
        if (BCryptUtil.checkPassword(userRequest.getPassword(), passwordHash)) {
            String newPasswordHash = BCryptUtil.hashPassword(userRequest.getNewPassword());
            userMapper.updatePassword(userRequest.getUsername(), newPasswordHash);
            userMapper.updateLastUpdate(userRequest.getUsername(), LocalDateTime.now());
        } else throw new BusinessException(2, 400, "Invalid password");
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
        redisService.setAdminStatus(id, true);
    }

    @LogRecord
    @Override
    public void unsetAdmin(Long id) {
        userMapper.unsetAdmin(id);
        redisService.setAdminStatus(id, false);
    }

    @LogRecord
    @Override
    public void banUser(Long id) {
        userMapper.banUser(id);
        redisService.setUserStatus(id, false);
    }

    @LogRecord
    @Override
    public void unbanUser(Long id) {
        userMapper.unbanUser(id);
        redisService.setUserStatus(id, true);
    }

    @Override
    public List<UserDTO> getUserList() {
        return userMapper.getUserList();
    }
}

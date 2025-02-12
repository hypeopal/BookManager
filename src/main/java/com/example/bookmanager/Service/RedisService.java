package com.example.bookmanager.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class RedisService {

    private final RedisTemplate<String, Object> redisTemplate;

    @Autowired
    public RedisService(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    private static final String ADMIN_KEY_PREFIX = "admin:";
    private static final String USER_STATUS_PREFIX = "user_status:";

    public void setAdminStatus(Long userId, boolean isAdmin) {
        redisTemplate.opsForValue().set(ADMIN_KEY_PREFIX + userId, isAdmin, 1, TimeUnit.HOURS);
    }

    public Boolean getAdminStatus(Long userId) {
        Boolean isAdmin = (Boolean) redisTemplate.opsForValue().get(ADMIN_KEY_PREFIX + userId);
        if (isAdmin != null) return isAdmin;
        else throw new DataRetrievalFailureException("Admin status not found");
    }

    public void setUserStatus(Long userId, boolean isBanned) {
        redisTemplate.opsForValue().set(USER_STATUS_PREFIX + userId, isBanned, 1, TimeUnit.HOURS);
    }

    public Boolean getUserStatus(Long userId) {
        Boolean isBanned = (Boolean) redisTemplate.opsForValue().get(USER_STATUS_PREFIX + userId);
        if (isBanned != null) return isBanned;
        else throw new DataRetrievalFailureException("User status not found");
    }
}

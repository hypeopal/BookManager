package com.example.bookmanager.Aspect;

import com.example.bookmanager.Mapper.UserMapper;
import com.example.bookmanager.Service.RedisService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class RedisFailureAspect {
    private final UserMapper userMapper;
    private final RedisService redisService;

    public RedisFailureAspect(UserMapper userMapper, RedisService redisService) {
        this.userMapper = userMapper;
        this.redisService = redisService;
    }

    @Around("execution(* com.example.bookmanager.Service.RedisService.*(..)) && !execution(* com.example.bookmanager.Service.RedisService.set*(..))")
    public Object handleRedisException(ProceedingJoinPoint joinPoint) throws Throwable {
        try {
            return joinPoint.proceed();
        } catch (Exception e) {
            return fallback(joinPoint);
        }
    }

    private Object fallback(ProceedingJoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        String methodName = joinPoint.getSignature().getName();

        if (methodName.equals("getAdminStatus")) {
            Long userId = (Long) args[0];
            Boolean isAdmin = userMapper.getAdminStatus(userId);
            redisService.setAdminStatus(userId, isAdmin);
            return isAdmin;
        } else if (methodName.equals("getUserStatus")) {
            Long userId = (Long) args[0];
            Boolean isBanned = userMapper.getUserStatus(userId);
            redisService.setUserStatus(userId, isBanned);
            return isBanned;
        }
        return null;
    }
}

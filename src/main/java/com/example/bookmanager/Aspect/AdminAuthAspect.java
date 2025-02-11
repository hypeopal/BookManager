package com.example.bookmanager.Aspect;

import com.example.bookmanager.Exception.BusinessException;
import com.example.bookmanager.Service.RedisService;
import com.example.bookmanager.Utils.ThreadLocalUtil;
import com.example.bookmanager.Utils.UserClaims;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class AdminAuthAspect {
    private final RedisService redisService;

    public AdminAuthAspect(RedisService redisService) {
        this.redisService = redisService;
    }

    @Around("@annotation(com.example.bookmanager.Annotation.RequireAdmin)")
    public Object checkAdminPermission(ProceedingJoinPoint joinPoint) throws Throwable {
        UserClaims claims = ThreadLocalUtil.get();
        if (claims == null || !redisService.getAdminStatus(claims.getId())) {
            throw new BusinessException(5, 403, "Forbidden: Admin access required");
        }
        return joinPoint.proceed();
    }
}

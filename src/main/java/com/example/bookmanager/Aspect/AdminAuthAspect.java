package com.example.bookmanager.Aspect;

import com.example.bookmanager.Annotation.RequireAdmin;
import com.example.bookmanager.Utils.ThreadLocalUtil;
import com.example.bookmanager.Utils.UserClaims;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Aspect
@Component
public class AdminAuthAspect {
    @Around("@annotation(requireAdmin)")
    public Object checkAdminPermission(ProceedingJoinPoint joinPoint, RequireAdmin requireAdmin) throws Throwable {
        UserClaims claims = ThreadLocalUtil.get();
        if (claims == null || !claims.isAdmin()) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Forbidden: Admin access required");
        }

        return joinPoint.proceed();
    }
}

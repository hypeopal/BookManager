package com.example.bookmanager.Interceptor;

import com.example.bookmanager.Exception.BusinessException;
import com.example.bookmanager.Utils.JWTUtil;
import com.example.bookmanager.Utils.ThreadLocalUtil;
import com.example.bookmanager.Utils.UserClaims;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class AuthInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull Object handler) {
        String token = request.getHeader("Authorization");
        if (token == null || token.isEmpty()) {
            throw new BusinessException(7, 401, "Unauthorized: No Token");
        }
        try {
            UserClaims claims = JWTUtil.parseToken(token);
            ThreadLocalUtil.set(claims);
            return true;
        } catch (ExpiredJwtException e) {
            throw new BusinessException(1, 401, "Token has Expired");
        } catch (Exception e) {
            throw new BusinessException(7, 401, "Unauthorized: Invalid Token");
        }
    }

    @Override
    public void afterCompletion(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull Object handler, Exception ex) {
        ThreadLocalUtil.remove();
    }
}

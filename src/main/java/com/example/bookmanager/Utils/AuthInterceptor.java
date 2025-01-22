package com.example.bookmanager.Utils;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import static jakarta.servlet.http.HttpServletResponse.SC_UNAUTHORIZED;

@Component
public class AuthInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull Object handler) throws Exception {
        String token = request.getHeader("Authorization");
        if (token == null || token.isEmpty()) {
            response.setStatus(SC_UNAUTHORIZED);
            response.getWriter().write("Unauthorized");
            return false;
        }
        try {
            UserClaims claims = JWTUtil.parseToken(token);
            ThreadLocalUtil.set(claims);
            return true;
        } catch (ExpiredJwtException e) {
            response.setStatus(SC_UNAUTHORIZED);
            response.getWriter().write("Token has Expired");
            return false;
        } catch (Exception e) {
            response.setStatus(SC_UNAUTHORIZED);
            response.getWriter().write("Unauthorized: Invalid Token");
            return false;
        }
    }

    @Override
    public void afterCompletion(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull Object handler, Exception ex) throws Exception {
        ThreadLocalUtil.remove();
    }
}

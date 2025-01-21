package com.example.bookmanager.Utils;


import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import static jakarta.servlet.http.HttpServletResponse.SC_UNAUTHORIZED;

public class AuthenticationFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String token = request.getHeader("Authorization");

        if (token == null || token.isEmpty()) {
            response.setStatus(SC_UNAUTHORIZED);
            response.getWriter().write("Unauthorized");
            return;
        }
        try {
            UserClaims claims = JWTUtil.parseToken(token);
            ThreadLocalUtil.set(claims);
            filterChain.doFilter(servletRequest, servletResponse);
        } catch (ExpiredJwtException e) {
            response.setStatus(SC_UNAUTHORIZED);
            response.getWriter().write("Token has Expired");
        } catch (Exception e) {
            response.setStatus(SC_UNAUTHORIZED);
            response.getWriter().write("Unauthorized: Invalid Token");
        }
    }
}

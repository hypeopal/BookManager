package com.example.bookmanager.Utils;


import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import static jakarta.servlet.http.HttpServletResponse.SC_UNAUTHORIZED;

public class AuthenticationFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String token = request.getHeader("Authorization");

        if (token == null || token.isEmpty()) {
            response.setStatus(SC_UNAUTHORIZED);
            response.getWriter().write("Unauthorized");
            return;
        }
        try {
            if (JWTUtil.isExpired(token)) {
                response.setStatus(SC_UNAUTHORIZED);
                response.getWriter().write("Token has Expired");
                return;
            }
            filterChain.doFilter(servletRequest, servletResponse);
        } catch (Exception e) {
            response.setStatus(SC_UNAUTHORIZED);
            response.getWriter().write("Unauthorized: Invalid Token");
        }
    }
}

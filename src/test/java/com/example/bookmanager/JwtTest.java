package com.example.bookmanager;

import com.example.bookmanager.Utils.JWTUtil;
import com.example.bookmanager.Utils.UserClaims;
import io.jsonwebtoken.ExpiredJwtException;
import org.junit.jupiter.api.Test;

public class JwtTest {
    @Test
    public void testGen() {
        UserClaims claims = new UserClaims("admin");
        String token = JWTUtil.generateToken(claims);
        System.out.println(token);
    }
    @Test
    public void testParse() {
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJ1c2VyTmFtZSI6ImFkbWluIiwiZXhwIjoxNzM3MzcwNzA1LCJpYXQiOjE3MzczNjM1MDV9.yArqQujPa1FH81qaDamSNVqsoDewAaLBQ1p0rHxdmdA";
        try {
            UserClaims claims = JWTUtil.parseToken(token);
            System.out.println(claims);
        } catch (ExpiredJwtException e) {
            System.out.println("expired");
        }
    }
}

package com.example.bookmanager;

import com.example.bookmanager.Utils.JWTUtil;
import com.example.bookmanager.Utils.UserClaims;
import io.jsonwebtoken.ExpiredJwtException;
import org.junit.jupiter.api.Test;

public class JwtTest {
    @Test
    public void testGen() {
        UserClaims claims = new UserClaims();
        claims.setUsername("test");
        String token = JWTUtil.generateToken(claims);
        System.out.println(token);
    }
    @Test
    public void testParse() {
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJzdGF0dXMiOnRydWUsImFkbWluIjpmYWxzZSwidXNlck5hbWUiOiJ0ZXN0IiwiZXhwIjoxNM4MTYzNzI2LCJpYXQiOjE3MzgxNTY1MjZ9.Bs6q1whwf9_DnFSTz89PLChQn7Kub5R1j4LvATUtTYk";
        try {
            UserClaims claims = JWTUtil.parseToken(token);
            System.out.println(claims);
        } catch (ExpiredJwtException e) {
            System.out.println("expired");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}

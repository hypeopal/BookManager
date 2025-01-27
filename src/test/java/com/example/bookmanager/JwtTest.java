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
        claims.setStatus(true);
        claims.setAdmin(false);
        String token = JWTUtil.generateToken(claims);
        System.out.println(token);
    }
    @Test
    public void testParse() {
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJzdGF0dXMiOnRydWUsImFkbWluIjpmYWxzZSwidXNlck5hbWUiOiJ0ZXN0IiwiZXhwIjoxNzM3OTc3OTc5LCJpYXQiOjE3Mzc5NzA3Nzl9.zj3UJ-tiTy7CC_8vI87Vfs7TcZ-eh7JEpm9GavadVto";
        try {
            UserClaims claims = JWTUtil.parseToken(token);
            System.out.println(claims);
        } catch (ExpiredJwtException e) {
            System.out.println("expired");
        }
    }
}
